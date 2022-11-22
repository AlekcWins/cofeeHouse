package ru.cofee.house.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cofee.house.api.IItemApi;
import ru.cofee.house.core.dto.ItemDto;
import ru.cofee.house.core.dto.mapper.CoffeeHouseMapper;
import ru.cofee.house.service.ItemService;
import ru.cofee.house.model.Item;

import static ru.cofee.house.controller.api.ItemController.API_PATH;

import java.util.List;

@RestController
@RequestMapping(value = API_PATH)
public class ItemController implements IItemApi {
    public static final  String API_PATH = "api/items";
    private final CoffeeHouseMapper mapper;
    private final ItemService itemService;

    @Autowired
    public ItemController(CoffeeHouseMapper mapper, ItemService itemService) {
        this.mapper = mapper;
        this.itemService = itemService;
    }


    @PostMapping
    public ItemDto createItme(ItemDto item) {
        Item mapItem = mapper.map(item, Item.class);
        Item createItem = itemService.create(mapItem);
        return mapper.map(createItem, ItemDto.class);
    }

    @GetMapping
    public List<ItemDto> getItems() {
       return mapper.mapAsList(itemService.getAll(), ItemDto.class);
    }

}
