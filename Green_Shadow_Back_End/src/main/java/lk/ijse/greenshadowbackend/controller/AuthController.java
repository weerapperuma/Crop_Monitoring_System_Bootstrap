package lk.ijse.greenshadowbackend.controller;

import jakarta.validation.Valid;
import lk.ijse.greenshadowbackend.dto.impl.UserDTO;
import lk.ijse.greenshadowbackend.exception.AlreadyExistsException;
import lk.ijse.greenshadowbackend.exception.DataPersistFailedException;
import lk.ijse.greenshadowbackend.jwtmodels.JwtAuthResponse;
import lk.ijse.greenshadowbackend.jwtmodels.SignIn;
import lk.ijse.greenshadowbackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/auth/")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping(value = "signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthResponse> signUp(@Valid @RequestBody UserDTO user) {
        logger.info("SignUp request received for email: {}", user.getEmail());
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            JwtAuthResponse jwtAuthResponse = authenticationService.signUp(user);
            if (jwtAuthResponse != null) {
                logger.info("User signed up successfully: {}", user.getEmail());
                return new ResponseEntity<>(jwtAuthResponse, HttpStatus.CREATED);
            } else {
                logger.warn("SignUp failed for email: {}", user.getEmail());
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (AlreadyExistsException e) {
            logger.warn("User already exists with email: {}", user.getEmail(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (DataPersistFailedException e) {
            logger.error("Failed to persist user with email: {}", user.getEmail(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error during signup for email: {}", user.getEmail(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "signin")
    public ResponseEntity<JwtAuthResponse> signIn(@RequestBody SignIn signIn) {
        logger.info("SignIn request received for email: {}", signIn.getEmail());
        try {
            JwtAuthResponse jwtAuthResponse = authenticationService.signIn(signIn);
            logger.info("User signed in successfully: {}", signIn.getEmail());
            return ResponseEntity.ok(jwtAuthResponse);
        } catch (Exception e) {
            logger.error("SignIn failed for email: {}", signIn.getEmail(), e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtAuthResponse> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        logger.info("Refresh token request received for token: {}", refreshToken);
        try {
            JwtAuthResponse jwtAuthResponse = authenticationService.refreshToken(refreshToken);
            logger.info("Refresh token issued successfully for token: {}", refreshToken);
            return ResponseEntity.ok(jwtAuthResponse);
        } catch (Exception e) {
            logger.error("Failed to refresh token for token: {}", refreshToken, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
