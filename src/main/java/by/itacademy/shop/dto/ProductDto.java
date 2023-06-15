package by.itacademy.shop.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ProductDto {

    Long id;
    String name;
    String description;
    BigDecimal price;

}
