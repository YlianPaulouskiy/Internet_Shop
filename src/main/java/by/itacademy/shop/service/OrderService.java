package by.itacademy.shop.service;

import by.itacademy.shop.dao.OrderDao;
import by.itacademy.shop.dto.OrderReadDto;
import by.itacademy.shop.mapper.OrderMapper;
import by.itacademy.shop.mapper.OrderMapperImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderService {

    private final OrderDao orderDao = OrderDao.getInstance();
    private final OrderMapper orderMapper = new OrderMapperImpl();

    public List<OrderReadDto> findAllByUserId(Long userId) {
        return orderDao.findAllByUserId(userId)
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<OrderReadDto> save(OrderReadDto orderReadDto) {
        return orderDao.save(orderMapper.toEntity(orderReadDto))
                .map(orderMapper::toDto);
    }

    public boolean delete(Long orderId) {
        return orderDao.delete(orderId);
    }

}
