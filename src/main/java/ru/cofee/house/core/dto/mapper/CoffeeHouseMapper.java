package ru.cofee.house.core.dto.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.cofee.house.core.dto.ItemDto;
import ru.cofee.house.core.dto.ItemFileDto;
import ru.cofee.house.core.dto.OrderDto;
import ru.cofee.house.core.dto.UserDto;
import ru.cofee.house.model.Item;
import ru.cofee.house.model.Order;
import ru.cofee.house.model.auth.User;

@Component
public class CoffeeHouseMapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Item.class, ItemDto.class)
                .byDefault()
                .register();

        factory.classMap(Item.class, ItemFileDto.class)
                .exclude("img")
                .byDefault()
                .register();

        factory.classMap(Order.class, OrderDto.class)
                .byDefault()
                .register();

        factory.classMap(User.class, UserDto.class)
                .byDefault()
                .register();
    }
}
