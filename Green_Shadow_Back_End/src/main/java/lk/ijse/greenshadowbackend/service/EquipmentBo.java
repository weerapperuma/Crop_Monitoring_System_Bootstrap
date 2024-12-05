package lk.ijse.greenshadowbackend.service;

import lk.ijse.greenshadowbackend.customObj.EquipmentResponse;
import lk.ijse.greenshadowbackend.dto.impl.EquipmentDTO;

import java.util.List;

public interface EquipmentBo {
    void saveEquipment(EquipmentDTO equipmentDTO);

    EquipmentResponse getEquipmentById(String equipmentId);

    void updateEquipment(EquipmentDTO equipmentDTO ,String staffId , String fieldCode , String equipmentId);

    void deleteEquipment(String equipmentId);

    List getAllEquipment();
}
