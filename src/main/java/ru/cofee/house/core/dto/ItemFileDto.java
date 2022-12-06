package ru.cofee.house.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class ItemFileDto {

    private long id;
    private float capacity;
    private float cost;
    private String name;
    private MultipartFile img;
}
