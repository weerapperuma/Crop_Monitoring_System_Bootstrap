package lk.ijse.gdse68.crop_monitoring_backend.service;

import lk.ijse.gdse68.crop_monitoring_backend.customObj.FieldResponse;
import lk.ijse.gdse68.crop_monitoring_backend.dto.impl.FieldDTO;

import java.util.List;

public interface FieldService {
    void saveField(FieldDTO fieldDTO);

    void updateField(FieldDTO fieldDTO, List<String> staffIds);

    FieldResponse getField(String fieldCode);

    void deleteField(String fieldCode);

    List getAllFields();
}
