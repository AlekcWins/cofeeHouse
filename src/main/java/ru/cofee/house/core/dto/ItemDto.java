package ru.cofee.house.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemDto {

    private long id;
    private float capacity;
    private float cost;
    private String name;
    private String pathImg;
}
