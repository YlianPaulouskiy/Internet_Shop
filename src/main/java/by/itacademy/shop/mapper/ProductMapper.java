package by.itacademy.shop.mapper;

import by.itacademy.shop.dto.ProductReadDto;
import by.itacademy.shop.entity.Product;
import org.mapstruct.Mapper;

@Mapper(uses = UserMapper.class)
public interface ProductMapper extends by.itacademy.shop.mapper.Mapper<Product, ProductReadDto> {

}
