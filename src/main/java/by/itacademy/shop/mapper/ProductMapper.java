package by.itacademy.shop.mapper;

import by.itacademy.shop.dto.ProductDto;
import by.itacademy.shop.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toEntity(ProductDto productDto);
    ProductDto toDto(Product product);

}
