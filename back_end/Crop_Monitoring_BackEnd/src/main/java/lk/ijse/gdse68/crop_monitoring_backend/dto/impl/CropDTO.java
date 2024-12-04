package lk.ijse.gdse68.crop_monitoring_backend.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lk.ijse.gdse68.crop_monitoring_backend.customObj.CropResponse;
import lk.ijse.gdse68.crop_monitoring_backend.dto.SuperDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CropDTO implements CropResponse, SuperDto {

    private String cropCode;
    @NotBlank
    @Pattern(regexp = "^[A-Z][a-zA-Z\\\\s]*$")
    @Size(min = 1, max = 50)
    private String cropCommonName;
    @NotBlank
    @Pattern(regexp = "^[A-Z][a-zA-Z0-9\\s]*$")
    @Size(min = 1, max = 50)
    private String cropScientificName;
    @NotBlank
    private String cropImage;
    @NotBlank
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]*$")
    private String cropCategory;
    @NotBlank
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]*$")
    private String cropSeason;
    private String field;
}
