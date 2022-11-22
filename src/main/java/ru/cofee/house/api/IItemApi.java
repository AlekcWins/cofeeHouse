package ru.cofee.house.api;

import io.swagger.v3.oas.annotations.Operation;
import ru.cofee.house.core.dto.ItemDto;

import java.util.List;

public interface IItemApi {
    @Operation(
            summary = "Создание",
            security = {}
    )
    ItemDto createItme(ItemDto item);

    @Operation(
            summary = "Получение",
            security = {}
    )
    List<ItemDto> getItems();
}
