package by.itacademy.shop.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Value
@Builder
public class StringUserDto {

    @NotBlank(message = "Name is required")
    String name;
    @NotBlank(message = "Lastname is required")
    String lastName;
    @NotBlank(message = "Email can't be empty")
    @Email(message = "Email is incorrect, follow the example: mymail@gmail.com")
    String email;
    @Pattern(regexp = "^\\+\\d{9,14}", message = "Phone need start by '+' and consist from 9 to 14 digits")
    String phone;
    @Size(min = 8, message = "The password must consist of at least 8 characters")
    String password;

}
