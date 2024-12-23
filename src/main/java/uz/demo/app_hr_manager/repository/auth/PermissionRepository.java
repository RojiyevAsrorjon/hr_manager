package uz.demo.app_hr_manager.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.demo.app_hr_manager.entity.auth.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
