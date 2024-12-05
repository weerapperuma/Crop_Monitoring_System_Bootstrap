package lk.ijse.gdse68.crop_monitoring_backend.service.impl;

import lk.ijse.gdse68.crop_monitoring_backend.Repository.EquipmentRepository;
import lk.ijse.gdse68.crop_monitoring_backend.Repository.FieldRepository;
import lk.ijse.gdse68.crop_monitoring_backend.Repository.StaffRepository;
import lk.ijse.gdse68.crop_monitoring_backend.customObj.EquipmentResponse;
import lk.ijse.gdse68.crop_monitoring_backend.customObj.errorResponse.EquipmentErrorResponse;
import lk.ijse.gdse68.crop_monitoring_backend.dto.impl.EquipmentDTO;
import lk.ijse.gdse68.crop_monitoring_backend.entity.Equipment;
import lk.ijse.gdse68.crop_monitoring_backend.entity.Field;
import lk.ijse.gdse68.crop_monitoring_backend.entity.Staff;
import lk.ijse.gdse68.crop_monitoring_backend.exception.DataPersistFailedException;
import lk.ijse.gdse68.crop_monitoring_backend.exception.NotFoundException;
import lk.ijse.gdse68.crop_monitoring_backend.service.EquipmentService;
import lk.ijse.gdse68.crop_monitoring_backend.util.AppUtil;
import lk.ijse.gdse68.crop_monitoring_backend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EquipmentServiceIMPL implements EquipmentService {

    private final EquipmentRepository equipmentRepository;

    private final Mapping mapping;

    private final StaffRepository staffRepository;

    private final FieldRepository fieldRepository;

    @Override
    public void saveEquipment(EquipmentDTO equipmentDTO) {
        equipmentDTO.setEquipmentId(AppUtil.createEquipmentCode());
        Equipment save = equipmentRepository.save(mapping.convertEquipmentDTOToEquipment(equipmentDTO));
        if (save == null) {
            throw new DataPersistFailedException("Equipment not saved");
        }
    }

    @Override
    public EquipmentResponse getEquipmentById(String equipmentId) {
        Optional<Equipment> equipment = equipmentRepository.findById(equipmentId);
        if (equipment.isPresent()){
            return mapping.convertEquipmentToEquipmentDTO(equipment.get());
        }else {
            return new EquipmentErrorResponse("Equipment not found", 404);
        }
    }

    @Override
    public void updateEquipment(EquipmentDTO equipmentDTO , String staffId , String fieldCode , String equipmentId) {

        Equipment equipment = equipmentRepository.findById(equipmentId).orElse(null);

        if (equipment != null){

            equipment = mapping.convertEquipmentDTOToEquipment(equipmentDTO);
            equipment.setEquipmentId(equipmentId);
            if (staffId.equals("N/A")) {
                equipment.setStaff(null);
            } else {
                Optional<Staff> optional = staffRepository.findById(staffId);
                if (optional.isPresent()){
                    Staff staff = optional.get();
                    equipment.setStaff(staff);
                }else {
                    throw new NotFoundException("Staff not found");
                }
            }

            if (fieldCode.equals("N/A")) {
                equipment.setField(null);
            } else {
                Optional<Field> optional = fieldRepository.findById(fieldCode);
                if (optional.isPresent()){
                    Field field = optional.get();
                    equipment.setField(field);
                }else {
                    throw new NotFoundException("Field not found");
                }
            }
        }

        if (equipment != null){
            Equipment save = equipmentRepository.save(equipment);
            if (save == null){
                throw new DataPersistFailedException("Equipment update failed");
            }
        }else {
            throw new NotFoundException("Equipment not found");
        }
    }

    @Override
    public void deleteEquipment(String equipmentId) {
        Optional<Equipment> equipment = equipmentRepository.findById(equipmentId);
        if (equipment.isPresent()){
            equipmentRepository.deleteById(equipmentId);
        }else {
            throw new NotFoundException("Equipment not found");
        }
    }

    @Override
    public List getAllEquipment() {
        return mapping.convertEquipmentListToEquipmentDTOList(equipmentRepository.findAll());
    }

}
