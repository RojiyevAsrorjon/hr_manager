package uz.demo.app_hr_manager.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionDto {

    private Long roleId;
    private Long permissionId;
}
