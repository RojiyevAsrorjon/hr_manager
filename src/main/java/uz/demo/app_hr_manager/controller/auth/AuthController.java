package uz.demo.app_hr_manager.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.demo.app_hr_manager.dto.request.LoginDto;
import uz.demo.app_hr_manager.dto.request.UserDto;
import uz.demo.app_hr_manager.dto.response.DataDto;
import uz.demo.app_hr_manager.entity.auth.User;
import uz.demo.app_hr_manager.service.auth.AuthService;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<DataDto<String>> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @PreAuthorize(value = "hasAuthority('ADD_USER')")
    @PostMapping("/add-user")
    public ResponseEntity<DataDto<User>> addUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(authService.addUser(userDto));
    }
}
