package by.itacademy.shop.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserReadDto {

    Long id;
    String name;
    String lastName;
    String email;
    String password;
    String phone;

}
