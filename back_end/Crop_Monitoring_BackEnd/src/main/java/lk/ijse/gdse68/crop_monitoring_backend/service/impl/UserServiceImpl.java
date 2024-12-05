package lk.ijse.gdse68.crop_monitoring_backend.service.impl;

import lk.ijse.gdse68.crop_monitoring_backend.Repository.UserRepository;
import lk.ijse.gdse68.crop_monitoring_backend.customObj.UserResponse;
import lk.ijse.gdse68.crop_monitoring_backend.customObj.errorResponse.UserErrorResponse;
import lk.ijse.gdse68.crop_monitoring_backend.dto.impl.UserDTO;
import lk.ijse.gdse68.crop_monitoring_backend.entity.User;
import lk.ijse.gdse68.crop_monitoring_backend.exception.DataPersistFailedException;
import lk.ijse.gdse68.crop_monitoring_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.spi.Mapping;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final Mapping mapping;
q2
    @Override
    public void saveUser(UserDTO user) {
        Optional<User> existsUser = userRepository.findByEmail(user.getEmail());
        if (!existsUser.isPresent()) {
            User save = userRepository.save(mapping.convertUserDTOToUser(user));
            if (save == null) {
                throw new DataPersistFailedException("User save failed");
            }
        }else {
            throw new DataPersistFailedException("User already exists");
        }
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return mapping.convertUserToUserDTO(user.get());
        }else {
            return new UserErrorResponse(0,"User not found");
        }
    }

    @Override
    public void updateUser(UserDTO user, String email) {
        Optional<User> existsUser = userRepository.findByEmail(email);
        if (existsUser.isPresent()) {
            existsUser.get().setPassword(user.getPassword());
        }else {
            throw new ChangeSetPersister.NotFoundException("User not exists");
        }
    }

    @Override
    public UserDetailsService userDetailsService() {
        return email ->
                userRepository.findByEmail(email)
                        .orElseThrow(()-> new ChangeSetPersister.NotFoundException("User Not found"));
    }
}
