package lk.ijse.gdse68.crop_monitoring_backend.service;

import lk.ijse.gdse68.crop_monitoring_backend.customObj.EquipmentResponse;
import lk.ijse.gdse68.crop_monitoring_backend.dto.impl.EquipmentDTO;

import java.util.List;

public interface EquipmentService {
    void saveEquipment(EquipmentDTO equipmentDTO);

    EquipmentResponse getEquipmentById(String equipmentId);

    void updateEquipment(EquipmentDTO equipmentDTO ,String staffId , String fieldCode , String equipmentId);

    void deleteEquipment(String equipmentId);

    List getAllEquipment();
}
