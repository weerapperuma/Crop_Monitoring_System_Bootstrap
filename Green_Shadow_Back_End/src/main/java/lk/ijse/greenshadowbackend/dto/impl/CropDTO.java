package lk.ijse.greenshadowbackend.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lk.ijse.greenshadowbackend.customObj.CropResponse;
import lk.ijse.greenshadowbackend.dto.SuperDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CropDTO implements CropResponse , SuperDto {
    private String cropCode;

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]*$")
    @Size(max = 50)
    private String cropCommonName;

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-zA-Z0-9\\s]*$")
    @Size(max = 100)
    private String cropScientificName;

    @NotBlank
    private String cropImage;

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]*$")
    private String category;

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]*$")
    private String cropSeason;

    private String fieldCode;
}
