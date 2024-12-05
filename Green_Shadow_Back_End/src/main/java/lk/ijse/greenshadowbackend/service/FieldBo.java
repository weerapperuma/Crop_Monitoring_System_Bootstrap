package lk.ijse.greenshadowbackend.service;

import lk.ijse.greenshadowbackend.customObj.FieldResponse;
import lk.ijse.greenshadowbackend.dto.impl.FieldDTO;

import java.util.List;

public interface FieldBo {
    void saveField(FieldDTO fieldDTO);

    void updateField(FieldDTO fieldDTO, List<String> staffIds);

    FieldResponse getField(String fieldCode);

    void deleteField(String fieldCode);

    List getAllFields();
}
