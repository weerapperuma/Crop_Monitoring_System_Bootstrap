package lk.ijse.gdse68.crop_monitoring_backend.service;

import lk.ijse.gdse68.crop_monitoring_backend.customObj.UserResponse;
import lk.ijse.gdse68.crop_monitoring_backend.dto.impl.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    void saveUser(UserDTO user);

    UserResponse getUserByEmail(String email);

    void updateUser(UserDTO user , String email);

    UserDetailsService userDetailsService();
}
