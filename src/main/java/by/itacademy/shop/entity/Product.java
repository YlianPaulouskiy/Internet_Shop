package by.itacademy.shop.entity;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "name")
public class Product {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;

    private User user;
}
