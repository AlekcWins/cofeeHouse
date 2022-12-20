package ru.cofee.house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cofee.house.model.Item;
import ru.cofee.house.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository repository;

    @Autowired
    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public void delete(long itemId) {
        repository.deleteById(itemId);
    }

    public Item update(Item item, long id) {
        return findById(id)
                .map(x -> {
                    if (item.getPathImg() != null)
                        x.setPathImg(item.getPathImg());
                    x.setCapacity(item.getCapacity());
                    x.setCost(item.getCost());
                    x.setName(item.getName());
                    return repository.save(x);
                })
                .orElseThrow(() -> new RuntimeException("Not found item for save"));


    }

    public Item create(Item item) {
        return repository.save(item);
    }

    public List<Item> getAll() {
        return repository.findAll();
    }

    public void hideItem(long id) {
        findById(id)
                .ifPresent(x -> {
                    x.setDeleted(true);
                    repository.save(x);
                });
    }

    public void showItem(long id) {
        findById(id)
                .ifPresent(x -> {
                    x.setDeleted(false);
                    repository.save(x);
                });
    }

    public Optional<Item> findById(long id) {
        return repository.findById(id);
    }
}
