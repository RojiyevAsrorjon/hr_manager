package uz.demo.app_hr_manager.service.auth;

import org.springframework.stereotype.Service;
import uz.demo.app_hr_manager.dto.request.LoginDto;
import uz.demo.app_hr_manager.dto.request.UserDto;
import uz.demo.app_hr_manager.dto.response.DataDto;
import uz.demo.app_hr_manager.entity.auth.User;

import javax.xml.crypto.Data;

@Service
public interface AuthService {

    DataDto<String> login(LoginDto loginDto);

    DataDto<User> addUser(UserDto userDto);
}
