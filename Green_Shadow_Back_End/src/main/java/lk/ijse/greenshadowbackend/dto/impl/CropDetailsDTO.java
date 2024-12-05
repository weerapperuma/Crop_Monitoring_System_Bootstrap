package lk.ijse.greenshadowbackend.dto.impl;

import jakarta.validation.constraints.NotBlank;
import lk.ijse.greenshadowbackend.customObj.CropDetailsResponse;
import lk.ijse.greenshadowbackend.dto.SuperDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CropDetailsDTO implements CropDetailsResponse , SuperDto {
    private String logCode;
    private Date logDate;

    @NotBlank
    private String logDetails;

    @NotBlank
    private String observedImage;

    @NotBlank
    private List<String> fieldCodes;

    @NotBlank
    private List<String> cropCodes;

    @NotBlank
    private List<String> staffIds;
}
