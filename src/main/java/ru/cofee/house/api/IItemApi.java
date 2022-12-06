package ru.cofee.house.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import ru.cofee.house.core.dto.ItemDto;
import ru.cofee.house.core.dto.ItemFileDto;

import java.util.List;

public interface IItemApi {
    @Operation(
            summary = "Создание",
            security = {}
    )
    ResponseEntity<ItemDto> createItme(ItemFileDto item);

    @Operation(
            summary = "Получение",
            security = {}
    )
    List<ItemDto> getItems();
}
