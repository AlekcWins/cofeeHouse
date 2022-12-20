package ru.cofee.house.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.cofee.house.core.dto.ItemDto;
import ru.cofee.house.core.dto.ItemFileDto;
import ru.cofee.house.core.dto.mapper.CoffeeHouseMapper;
import ru.cofee.house.model.Item;
import ru.cofee.house.service.ItemService;
import ru.cofee.house.service.auth.UserDetailsServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.cofee.house.controller.api.ItemController.API_PATH;

@RestController
@RequestMapping(value = API_PATH)
@Slf4j
public class ItemController {
    public static final String API_PATH = "api/items";
    private final CoffeeHouseMapper mapper;
    private final ItemService itemService;
    private final UserDetailsServiceImpl userService;

    @Autowired
    public ItemController(CoffeeHouseMapper mapper, ItemService itemService, UserDetailsServiceImpl userDetailsService) {
        this.mapper = mapper;
        this.itemService = itemService;
        this.userService = userDetailsService;
    }


    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ItemDto> createItem(ItemFileDto item) {
        Item mapItem = mapper.map(item, Item.class);
        try {
            return saveUploadedFile(item.getImg())
                    .map(fileName -> {
                        mapItem.setPathImg(fileName);
                        Item createItem = itemService.create(mapItem);
                        return ResponseEntity.ok(mapper.map(createItem, ItemDto.class));
                    })
                    .orElse(ResponseEntity.badRequest().build());

        } catch (IOException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<ItemDto> getItems(Authentication authentication, @RequestParam boolean withDeleted) {
        if (withDeleted && userService.isIamAdmin(authentication)) {
            return mapper.mapAsList(itemService.getAll(), ItemDto.class);
        }
        return mapper.mapAsList(
                itemService.getAll().stream().filter(x -> !x.isDeleted()).collect(Collectors.toList()),
                ItemDto.class);
    }

    @PostMapping("hideItem")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void hideItem(long id) {
        itemService.hideItem(id);
    }

    @PostMapping("showItem")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void showItem(long id) {
        itemService.showItem(id);
    }


    private Optional<String> saveUploadedFile(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Resource resource = new ClassPathResource("static/static/images/items");
            String genFileName = UUID.randomUUID()
                    + file.getOriginalFilename();
            Path path = Paths.get(resource.getFile().getAbsolutePath(), genFileName);
            Files.write(path, bytes);
            return Optional.of(genFileName);
        }
        return Optional.empty();
    }
}
