package ru.cofee.house.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cofee.house.model.Item;
import ru.cofee.house.model.Order;
import ru.cofee.house.model.OrderItem;
import ru.cofee.house.model.Status;
import ru.cofee.house.repository.ItemRepository;
import ru.cofee.house.repository.OrderItemRepository;
import ru.cofee.house.repository.OrderRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderService(OrderRepository repository, ItemRepository itemRepository, OrderItemRepository orderItemRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.orderItemRepository = orderItemRepository;
    }


    public Order addOrderItem(String username, long itemId) throws NotFoundException {
        Order order = findMyDraftOrder(username);

        Optional<OrderItem> findOrderItem = orderItemRepository
                .findByItemIdAndOrderId(itemId, order.getId());

        if (findOrderItem.isPresent()) {
            OrderItem orderItem = findOrderItem.get();
            orderItem.setCount(orderItem.getCount() + 1);
            orderItemRepository.save(orderItem);
            return findMyDraftOrder(username);
        } else {
            Optional<Item> findItem = itemRepository.findById(itemId);
            if (findItem.isPresent()) {
                List<OrderItem> items = order.getItems();
                Item item = findItem.get();
                OrderItem orderItem = new OrderItem();
                orderItem.setItem(item);
                orderItem.setCount(1);
                //todo order refactor
                orderItem.setOrder(order);
                items.add(orderItemRepository.save(orderItem));
                return repository.save(order);
            }
            throw new NotFoundException("Not found item for save");
        }
    }


    private Order createDraftOrder(String username) {
        Order newOrder = new Order();
        newOrder.setUserName(username);
        newOrder.setStatus(Status.DRAFT);
        return repository.save(newOrder);
    }

    public Order findMyDraftOrder(String username) {
        Optional<Order> findOrder = repository
                .findOrderByUserNameAndStatus(username, Status.DRAFT);
        if (findOrder.isPresent()) {
            return findOrder.get();
        }
        return createDraftOrder(username);
    }

    public Order confirmMyOrder(String username) {
        Order myOrder = findMyDraftOrder(username);
        myOrder.setStatus(Status.ACCEPTED);
        return repository.save(myOrder);
    }

    public Order issueOrder(long orderId) throws NotFoundException {
        return repository.findById(orderId)
                .map(o -> {
                    o.setStatus(Status.ISSUED);
                    return repository.save(o);
                })
                .orElseThrow(() -> new NotFoundException("not found order with id: " + orderId));
    }

    public List<Order> getAllForWork() {
        return repository.findAll().stream()
                .filter(x -> !x.getStatus().equals(Status.DRAFT) && !x.getStatus().equals(Status.ISSUED))
                .collect(Collectors.toList());
    }

    public List<Order> allComplete() {
        return repository.findAll().stream()
                .filter(x -> x.getStatus().equals(Status.ISSUED))
                .collect(Collectors.toList());
    }

    public Order updateOrderItem(String username, long itemId, int count) throws NotFoundException {
        Order order = findMyDraftOrder(username);

        Optional<OrderItem> findOrderItem = orderItemRepository
                .findByItemIdAndOrderId(itemId, order.getId());

        if (findOrderItem.isPresent()) {
            OrderItem orderItem = findOrderItem.get();
            orderItem.setCount(count);
            orderItemRepository.save(orderItem);
            return findMyDraftOrder(username);
        }
        throw new NotFoundException("Not found item for save");
    }

    public Order deleteOrderItem(String username, long itemId) throws NotFoundException {
        Order order = findMyDraftOrder(username);

        Optional<OrderItem> findOrderItem = orderItemRepository
                .findByItemIdAndOrderId(itemId, order.getId());

        if (findOrderItem.isPresent()) {
            OrderItem orderItem = findOrderItem.get();
            orderItemRepository.delete(orderItem);
            return findMyDraftOrder(username);
        }
        throw new NotFoundException("Not found item for save");
    }

    public List<Order> getMyActiveOrders(String username) {
        return getAllForWork().stream().filter(x -> x.getUserName().equals(username)).collect(Collectors.toList());
    }

    public List<Order> getMyCompleteOrders(String username) {
        return allComplete().stream().filter(x -> x.getUserName().equals(username)).collect(Collectors.toList());
    }
}
