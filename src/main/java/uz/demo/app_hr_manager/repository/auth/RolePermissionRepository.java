package uz.demo.app_hr_manager.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import uz.demo.app_hr_manager.entity.auth.RolePermission;

@Service
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
}
