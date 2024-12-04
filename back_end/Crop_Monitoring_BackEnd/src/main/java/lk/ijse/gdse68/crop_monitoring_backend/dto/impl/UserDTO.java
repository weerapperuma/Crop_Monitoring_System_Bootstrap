package lk.ijse.gdse68.crop_monitoring_backend.dto.impl;

import jakarta.validation.constraints.*;
import lk.ijse.gdse68.crop_monitoring_backend.customObj.UserResponse;
import lk.ijse.gdse68.crop_monitoring_backend.dto.SuperDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO implements UserResponse, SuperDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotNull
    @Pattern(regexp = "OTHER|MANAGER|ADMINISTRATIVE|SCIENTIST")
    private String role;
}
