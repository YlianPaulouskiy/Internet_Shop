package by.itacademy.shop.entity;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "email")
public class User {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String phone;

    private List<Product> products;
    private Address address;

}
