package lk.ijse.gdse68.crop_monitoring_backend.service;

import lk.ijse.gdse68.crop_monitoring_backend.customObj.CropResponse;
import lk.ijse.gdse68.crop_monitoring_backend.dto.impl.CropDTO;

import java.util.List;

public interface CropService {
    void saveCrop(CropDTO cropDTO, String fieldCode);

    CropResponse getCrop(String id);

    void updateCrop(CropDTO cropDTO, String fieldCode, String id);

    void deleteCrop(String id);

    List getAllCrops();
}
