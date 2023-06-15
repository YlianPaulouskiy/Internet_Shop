package by.itacademy.shop.service;

import by.itacademy.shop.dao.AddressDao;
import by.itacademy.shop.dto.AddressReadDto;
import by.itacademy.shop.mapper.AddressMapper;
import by.itacademy.shop.mapper.AddressMapperImpl;
import lombok.Cleanup;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import java.util.Optional;

public class AddressService {

    private final AddressDao addressDao = AddressDao.getInstance();
    private final AddressMapper addressMapper = new AddressMapperImpl();

    public Optional<AddressReadDto> findByUserId(Long userId) {
        return addressDao.findByUserId(userId)
                .map(addressMapper::toDto);
    }

    public Optional<AddressReadDto> save(Long userId, AddressReadDto addressDto) {
        checkValidation(addressDto);
        return addressDao.save(userId, addressMapper.toEntity(addressDto))
                .map(addressMapper::toDto);
    }

    public boolean update(Long userId, AddressReadDto addressDto) {
        return addressDao.update(userId, addressMapper.toEntity(addressDto));
    }

    public boolean delete(Long userId) {
        return addressDao.deleteByUserId(userId);
    }

    private void checkValidation(AddressReadDto addressReadDto) {
        @Cleanup var validationFactory = Validation.buildDefaultValidatorFactory();
        var validator = validationFactory.getValidator();
        var validationResult = validator.validate(addressReadDto);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }
    }
}

