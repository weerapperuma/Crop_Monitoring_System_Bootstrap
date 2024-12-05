package lk.ijse.greenshadowbackend.service;

import lk.ijse.greenshadowbackend.customObj.UserResponse;
import lk.ijse.greenshadowbackend.dto.impl.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserBo {
    void saveUser(UserDTO user);

    UserResponse getUserByEmail(String email);

    void updateUser(UserDTO user , String email);

    UserDetailsService userDetailsService();
}
