package uz.demo.app_hr_manager.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.demo.app_hr_manager.dto.request.LoginDto;
import uz.demo.app_hr_manager.dto.request.UserDto;
import uz.demo.app_hr_manager.entity.auth.User;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDto userDto);
}
