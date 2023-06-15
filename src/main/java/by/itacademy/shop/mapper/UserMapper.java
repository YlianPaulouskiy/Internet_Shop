package by.itacademy.shop.mapper;

import by.itacademy.shop.dto.StringUserDto;
import by.itacademy.shop.dto.UserReadDto;
import by.itacademy.shop.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper extends by.itacademy.shop.mapper.Mapper<User, UserReadDto> {

    User toEntity(StringUserDto userDto);

}
