package ru.cofee.house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cofee.house.repository.ItemRepository;
import ru.cofee.house.model.Item;

import java.util.List;

@Service
public class ItemService {

    private  final ItemRepository repository;

    @Autowired
    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public Item create(Item item){
        return repository.save(item);
    }

    public List<Item> getAll(){
        return  repository.findAll();
    }
}
