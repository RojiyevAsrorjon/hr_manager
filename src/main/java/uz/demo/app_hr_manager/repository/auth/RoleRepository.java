package uz.demo.app_hr_manager.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.demo.app_hr_manager.entity.auth.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
