package lk.ijse.gdse68.crop_monitoring_backend.dto.impl;

import jakarta.validation.constraints.NotBlank;
import lk.ijse.gdse68.crop_monitoring_backend.customObj.CropDetailsResponse;
import lk.ijse.gdse68.crop_monitoring_backend.dto.SuperDto;
import lk.ijse.gdse68.crop_monitoring_backend.entity.Crop;
import lk.ijse.gdse68.crop_monitoring_backend.entity.Field;
import lk.ijse.gdse68.crop_monitoring_backend.entity.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CropDetailsDTO implements CropDetailsResponse, SuperDto {
    private String logCode;
    private Date logDate;
    @NotBlank
    private String logDetails;
    @NotBlank
    private String observedImage;
    @NotBlank
    private List<String> fieldIds;
    @NotBlank
    private List<String> cropIds;
    @NotBlank
    private List<String> staffIds;
}
