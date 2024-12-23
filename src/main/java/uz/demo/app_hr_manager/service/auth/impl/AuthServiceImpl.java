package uz.demo.app_hr_manager.service.auth.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.demo.app_hr_manager.configuration.custom.CustomUserDetails;
import uz.demo.app_hr_manager.configuration.security.JwtProvider;
import uz.demo.app_hr_manager.dto.request.LoginDto;
import uz.demo.app_hr_manager.dto.request.UserDto;
import uz.demo.app_hr_manager.dto.response.DataDto;
import uz.demo.app_hr_manager.entity.auth.Permission;
import uz.demo.app_hr_manager.entity.auth.Role;
import uz.demo.app_hr_manager.entity.auth.RolePermission;
import uz.demo.app_hr_manager.entity.auth.User;
import uz.demo.app_hr_manager.exceptions.BadRequestException;
import uz.demo.app_hr_manager.exceptions.NotFoundException;
import uz.demo.app_hr_manager.mapper.UserMapper;
import uz.demo.app_hr_manager.repository.auth.PermissionRepository;
import uz.demo.app_hr_manager.repository.auth.RolePermissionRepository;
import uz.demo.app_hr_manager.repository.auth.RoleRepository;
import uz.demo.app_hr_manager.repository.auth.UserRepository;
import uz.demo.app_hr_manager.service.auth.AuthService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;

    @Override
    public DataDto<String> login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

            User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
            Optional<Role> optionalRole = roleRepository.findById(user.getRoleId());
            if (optionalRole.isEmpty()) {
                throw new RuntimeException("User role not found");
            }

            String token = jwtProvider.generateToken(user.getPhoneNumber(), optionalRole.get().getName());
            return new DataDto<>(token);

        } catch (Exception e) {
            throw new UsernameNotFoundException("Login or password is incorrect");
        }
    }

    @Override
    public DataDto<User> addUser(UserDto userDto) {
        if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
            throw new BadRequestException("This number is already registered!");
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userMapper.toEntity(userDto);

        User savedUser = userRepository.save(user);

        return new DataDto<>(savedUser);
    }

}
