package lk.ijse.greenshadowbackend.service;

import lk.ijse.greenshadowbackend.customObj.CropResponse;
import lk.ijse.greenshadowbackend.dto.impl.CropDTO;

import java.util.List;

public interface CropBo {
    void saveCrop(CropDTO cropDTO, String fieldCode);

    CropResponse getCrop(String id);

    void updateCrop(CropDTO cropDTO, String fieldCode, String id);

    void deleteCrop(String id);

    List getAllCrops();
}
