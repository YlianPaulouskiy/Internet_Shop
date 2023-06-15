package by.itacademy.shop.mapper;

import by.itacademy.shop.dto.AddressReadDto;
import by.itacademy.shop.entity.Address;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper extends by.itacademy.shop.mapper.Mapper<Address, AddressReadDto> {

}
