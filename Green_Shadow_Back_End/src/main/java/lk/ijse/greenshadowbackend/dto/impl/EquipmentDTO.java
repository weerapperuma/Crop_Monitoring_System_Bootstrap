package lk.ijse.greenshadowbackend.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lk.ijse.greenshadowbackend.customObj.EquipmentResponse;
import lk.ijse.greenshadowbackend.dto.SuperDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDTO implements EquipmentResponse , SuperDto {

    private String equipmentId;

    @NotBlank
    private String equipmentName;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$")
    private String equipmentType;

    @NotBlank
    private String status;

    private String fieldCode;

    private String staffId;
}
