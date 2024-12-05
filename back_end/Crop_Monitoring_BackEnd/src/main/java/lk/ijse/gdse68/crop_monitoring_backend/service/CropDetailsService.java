package lk.ijse.gdse68.crop_monitoring_backend.service;

import lk.ijse.gdse68.crop_monitoring_backend.customObj.CropDetailsResponse;
import lk.ijse.gdse68.crop_monitoring_backend.dto.impl.CropDetailsDTO;

import java.util.List;

public interface CropDetailsService {
    void saveCropDetails(CropDetailsDTO cropDetailsDTO);

    void updateCropDetails(CropDetailsDTO cropDetailsDTO , String logCode);

    CropDetailsResponse findCropDetailsByLogCode(String logCode);

    void deleteCropDetailsByLogCode(String logCode);

    List<CropDetailsDTO> getAllCropDetails();
}
