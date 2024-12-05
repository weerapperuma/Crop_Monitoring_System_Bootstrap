package lk.ijse.gdse68.crop_monitoring_backend.service.impl;

import lk.ijse.gdse68.crop_monitoring_backend.Repository.UserRepository;
import lk.ijse.gdse68.crop_monitoring_backend.dto.impl.UserDTO;
import lk.ijse.gdse68.crop_monitoring_backend.entity.User;
import lk.ijse.gdse68.crop_monitoring_backend.exception.AlreadyExistsException;
import lk.ijse.gdse68.crop_monitoring_backend.exception.DataPersistFailedException;
import lk.ijse.gdse68.crop_monitoring_backend.jwtmodels.JwtAuthResponse;
import lk.ijse.gdse68.crop_monitoring_backend.jwtmodels.SignIn;
import lk.ijse.gdse68.crop_monitoring_backend.service.AuthenticationService;
import lk.ijse.gdse68.crop_monitoring_backend.service.JWTService;
import lk.ijse.gdse68.crop_monitoring_backend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceIMPL implements AuthenticationService {

    private final UserRepository userService;
    private final JWTService jwtService;
    private final Mapping mapping;
    //utils
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthResponse signIn(SignIn signIn) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getEmail(),signIn.getPassword()));
        var userByEmail = userService.findByEmail(signIn.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var generatedToken = jwtService.generateToken(userByEmail);
        return JwtAuthResponse.builder().token(generatedToken).build() ;
    }

    @Override
    public JwtAuthResponse signUp(UserDTO signUp) {
        Optional<User> existsUser = userService.findByEmail(signUp.getEmail());
        if (!existsUser.isPresent()){
            User savedUser = userService.save(mapping.convertUserDTOToUser(signUp));
            if (savedUser == null){
                throw new DataPersistFailedException("User Save Failed");
            }
            var genToken = jwtService.generateToken(savedUser);
            return JwtAuthResponse.builder().token(genToken).build();
        }else {
            throw new AlreadyExistsException("User already exists");
        }
    }

    @Override
    public JwtAuthResponse refreshToken(String accessToken) {
        var userName = jwtService.extractUsername(accessToken);
        var userEntity =
                userService.findByEmail(userName).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var refreshToken = jwtService.refreshToken(userEntity);
        return JwtAuthResponse.builder().token(refreshToken).build();
    }
}
