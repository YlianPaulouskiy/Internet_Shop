package by.itacademy.shop.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@Builder
public class AddressReadDto {

    @NotBlank(message = "City is required")
    String city;
    @NotBlank(message = "Street is required")
    String street;
    @NotBlank(message = "House is required")
    String house;
    String flat;

}
