package ru.cofee.house.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.cofee.house.api.IItemApi;
import ru.cofee.house.core.dto.ItemDto;
import ru.cofee.house.core.dto.ItemFileDto;
import ru.cofee.house.core.dto.mapper.CoffeeHouseMapper;
import ru.cofee.house.model.Item;
import ru.cofee.house.service.ItemService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static ru.cofee.house.controller.api.ItemController.API_PATH;

@RestController
@RequestMapping(value = API_PATH)
public class ItemController implements IItemApi {
    public static final String API_PATH = "api/items";
    private final CoffeeHouseMapper mapper;
    private final ItemService itemService;

    @Autowired
    public ItemController(CoffeeHouseMapper mapper, ItemService itemService) {
        this.mapper = mapper;
        this.itemService = itemService;
    }


    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ItemDto> createItme(ItemFileDto item) {
        Item mapItem = mapper.map(item, Item.class);
        try {
            // Save as you want as per requiremens
            String fileName = saveUploadedFile(item.getImg());
            mapItem.setPathImg(fileName);
            Item createItem = itemService.create(mapItem);

            return new ResponseEntity(mapper.map(createItem, ItemDto.class), HttpStatus.OK);
        } catch (IOException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<ItemDto> getItems() {
        return mapper.mapAsList(itemService.getAll(), ItemDto.class);
    }


    //todo service
    private String saveUploadedFile(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Resource resource = new ClassPathResource("static/static/images/items");
            String genFileName = UUID.randomUUID()
                    + file.getOriginalFilename();
            Path path = Paths.get(resource.getFile().getAbsolutePath() + "/" + genFileName);
            Files.write(path, bytes);
            return genFileName;
        }
        //todo
        return "";
    }
}
