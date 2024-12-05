package lk.ijse.gdse68.crop_monitoring_backend.service;

import lk.ijse.gdse68.crop_monitoring_backend.customObj.UserResponse;
import lk.ijse.gdse68.crop_monitoring_backend.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    void saveUser(User user);
    UserResponse getUserByEmail(String email);
    void updateUser(User user);
    UserDetailsService userDetailsService();
}
