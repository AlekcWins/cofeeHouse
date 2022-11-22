package ru.cofee.house.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.cofee.house.model.Status;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private long id;
    private Status status;
    private List<ItemDto> items;

    //todo add user
}
