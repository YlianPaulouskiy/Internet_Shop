package by.itacademy.shop.mapper;

import by.itacademy.shop.dto.OrderReadDto;
import by.itacademy.shop.entity.Order;
import org.mapstruct.Mapper;

@Mapper(uses = {UserMapper.class, ProductMapper.class})
public interface OrderMapper extends by.itacademy.shop.mapper.Mapper<Order, OrderReadDto> {

}
