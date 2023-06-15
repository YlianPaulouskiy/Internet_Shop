package by.itacademy.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    private Long id;
    private String city;
    private String street;
    private String house;
    private String flat;

}
