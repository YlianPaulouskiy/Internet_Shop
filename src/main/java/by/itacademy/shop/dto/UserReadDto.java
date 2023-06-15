package by.itacademy.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import javax.validation.constraints.Size;

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
