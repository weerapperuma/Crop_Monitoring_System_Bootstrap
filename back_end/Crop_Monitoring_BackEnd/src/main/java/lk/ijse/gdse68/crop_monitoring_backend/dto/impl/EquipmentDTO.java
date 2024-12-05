package lk.ijse.gdse68.crop_monitoring_backend.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lk.ijse.gdse68.crop_monitoring_backend.customObj.EquipmentResponse;
import lk.ijse.gdse68.crop_monitoring_backend.dto.SuperDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentDTO implements EquipmentResponse, SuperDto {
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
