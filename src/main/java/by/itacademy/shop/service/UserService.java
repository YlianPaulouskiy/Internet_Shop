package by.itacademy.shop.service;

import by.itacademy.shop.dao.UserDao;
import by.itacademy.shop.dto.StringUserDto;
import by.itacademy.shop.dto.UserReadDto;
import by.itacademy.shop.exception.EntityExistsException;
import by.itacademy.delivery.mapper.UserMapperImpl;
import by.itacademy.shop.mapper.UserMapper;
import lombok.Cleanup;


import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validation;
import java.util.Optional;

@Valid
public class UserService {

    private final UserDao userDao = UserDao.getInstance();
    private final UserMapper userMapper = new UserMapperImpl();

    public Optional<UserReadDto> create(StringUserDto userDto) {
        @Cleanup var validationFactory = Validation.buildDefaultValidatorFactory();
        var validator = validationFactory.getValidator();
        var validationResult = validator.validate(userDto);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }
        var user = userMapper.toEntity(userDto);
        if (userDao.findAll().contains(user)) {
            throw new EntityExistsException("User email " + user.getEmail() + " already exists.");
        }
        return userDao.save(user).map(userMapper::toDto);
    }

    public Optional<UserReadDto> findById(Long id) {
        return userDao.findById(id).map(userMapper::toDto);
    }

    public Optional<UserReadDto> login(String email, String password) {
        return userDao.login(email, password).map(userMapper::toDto);
    }

}
