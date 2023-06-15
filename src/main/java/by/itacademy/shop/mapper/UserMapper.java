package by.itacademy.shop.mapper;

import by.itacademy.shop.dto.StringUserDto;
import by.itacademy.shop.dto.UserReadDto;
import by.itacademy.shop.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User toEntity(StringUserDto userDto);

    User toEntity(UserReadDto userReadDto);

    UserReadDto toDto(User user);

}
