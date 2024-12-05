package lk.ijse.gdse68.crop_monitoring_backend.service;
import lk.ijse.gdse68.crop_monitoring_backend.dto.impl.UserDTO;
import lk.ijse.gdse68.crop_monitoring_backend.jwtmodels.JwtAuthResponse;
import lk.ijse.gdse68.crop_monitoring_backend.jwtmodels.SignIn;

public interface AuthenticationService {
    JwtAuthResponse signIn(SignIn signIn);
    JwtAuthResponse signUp(UserDTO signUp);
    JwtAuthResponse refreshToken(String accessToken);
}
