package uz.demo.app_hr_manager.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String firstname;
    private String lastname;

    private String phoneNumber;

    private String password;

    private Long roleId;
}
