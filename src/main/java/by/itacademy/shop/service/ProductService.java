package by.itacademy.shop.service;

import by.itacademy.shop.dao.ProductDao;
import by.itacademy.shop.dto.ProductReadDto;
import by.itacademy.shop.exception.EntityExistsException;
import by.itacademy.shop.mapper.ProductMapper;
import by.itacademy.shop.mapper.ProductMapperImpl;
import lombok.Cleanup;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductService {

    private final ProductDao productDao = ProductDao.getInstance();
    private final ProductMapper productMapper = new ProductMapperImpl();

    public Optional<ProductReadDto> findById(Long id) {
        return productDao.findById(id).map(productMapper::toDto);
    }

    public List<ProductReadDto> findAll() {
        return productDao.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductReadDto> findAllByUserId(Long userId) {
        return productDao.findAllByUserId(userId)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ProductReadDto> save(ProductReadDto productReadDto) {
        checkValidation(productReadDto);
        checkExists(productReadDto);
        return productDao.save(productMapper.toEntity(productReadDto))
                .map(productMapper::toDto);
    }

    public boolean delete(Long id) {
        return productDao.delete(id);
    }

    private void checkValidation(ProductReadDto productReadDto) {
        @Cleanup var validationFactory = Validation.buildDefaultValidatorFactory();
        var validator = validationFactory.getValidator();
        var validationResult = validator.validate(productReadDto);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }
    }

    private void checkExists(ProductReadDto productReadDto) {
        if (productDao.findAll().contains(productMapper.toEntity(productReadDto))) {
            throw new EntityExistsException("Product name " + productReadDto.getName() + " already exists.");
        }
    }
}
