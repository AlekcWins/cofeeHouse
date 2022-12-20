package ru.cofee.house.service;

import javassist.NotFoundException;
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

    public Item update(Item item) throws NotFoundException {
        Optional<Item> find = repository.findById(item.getId());
        if (find.isPresent()) {
            return repository.save(item);
        }
        throw new NotFoundException("Not found item for save");
    }

    public Item create(Item item) {
        return repository.save(item);
    }

    public List<Item> getAll() {
        return repository.findAll();
    }

    public void hideItem(long id) {
        repository.findById(id)
                .ifPresent(x -> {
                    x.setDeleted(true);
                    repository.save(x);
                });
    }

    public void showItem(long id) {
        repository.findById(id)
                .ifPresent(x -> {
                    x.setDeleted(false);
                    repository.save(x);
                });
    }
}
