package uz.demo.app_hr_manager.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.demo.app_hr_manager.entity.auth.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findByPhoneNumber(String username);

     boolean existsByPhoneNumber(String phoneNumber);
}
