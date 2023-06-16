package by.itacademy.shop.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Value
@EqualsAndHashCode(of = "name")
public class ProductReadDto {

    Long id;
    @NotBlank(message = "Name can't be empty")
    String name;
    String description;
    @NotNull(message = "Price can't be null")
    BigDecimal price;

    UserReadDto user;

}

