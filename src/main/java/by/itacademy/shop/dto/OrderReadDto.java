package by.itacademy.shop.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class OrderReadDto {

    Long id;
    LocalDateTime registrationTime;
    UserReadDto user;
    List<ProductReadDto> products;

}
