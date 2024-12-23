package uz.demo.app_hr_manager.configuration.custom;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.demo.app_hr_manager.entity.auth.Permission;
import uz.demo.app_hr_manager.entity.auth.Role;
import uz.demo.app_hr_manager.entity.auth.User;
import uz.demo.app_hr_manager.repository.auth.PermissionRepository;
import uz.demo.app_hr_manager.repository.auth.RoleRepository;
import uz.demo.app_hr_manager.repository.auth.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found : " + username));
        Long id = user.getRoleId();
        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new RuntimeException("User role not found: " + username));

        List<Permission> permissions = permissionRepository.findAllById(role.getPermissionsId());

        return new CustomUserDetails(user, role, permissions);

    }
}
