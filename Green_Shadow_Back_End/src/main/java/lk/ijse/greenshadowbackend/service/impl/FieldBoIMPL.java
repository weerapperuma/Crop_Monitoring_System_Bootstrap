package lk.ijse.greenshadowbackend.service.impl;

import lk.ijse.greenshadowbackend.Repository.FieldRepository;
import lk.ijse.greenshadowbackend.Repository.StaffRepository;
import lk.ijse.greenshadowbackend.customObj.errorRespose.FieldErrorResponse;
import lk.ijse.greenshadowbackend.customObj.FieldResponse;
import lk.ijse.greenshadowbackend.dto.impl.FieldDTO;
import lk.ijse.greenshadowbackend.entity.Field;
import lk.ijse.greenshadowbackend.entity.Staff;
import lk.ijse.greenshadowbackend.exception.DataPersistFailedException;
import lk.ijse.greenshadowbackend.exception.NotFoundException;
import lk.ijse.greenshadowbackend.service.FieldBo;
import lk.ijse.greenshadowbackend.util.AppUtil;
import lk.ijse.greenshadowbackend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FieldBoIMPL implements FieldBo {

    private final FieldRepository fieldRepository;
    private final StaffRepository staffRepository;

    private final Mapping mapping;

    @Override
    public void saveField(FieldDTO fieldDTO) {
        fieldDTO.setFieldCode(AppUtil.createFieldCode());
        Field save = fieldRepository.save(mapping.convertFieldDTOToField(fieldDTO));
        if (save == null){
            throw new DataPersistFailedException("Field save failed");
        }
    }

    @Override
    public void updateField(FieldDTO fieldDTO , List<String> staffIds) {

        Optional<Field> field = fieldRepository.findById(fieldDTO.getFieldCode());
        if (field.isPresent()) {
            Field TempField1 = mapping.convertFieldDTOToField(fieldDTO);
            List<Staff> staff = new ArrayList<>();
            for (String staffId : staffIds) {
                Optional<Staff> optional = staffRepository.findById(staffId);
                optional.ifPresent(staff::add);
            }
            TempField1.setStaff(staff);
            Field save = fieldRepository.save(TempField1);
            if (save == null) {
                throw new DataPersistFailedException("Field update failed");
            }
        }else {
            throw new NotFoundException("Field not found");
        }
    }

    @Override
    public FieldResponse getField(String fieldCode) {
        Optional<Field> field = fieldRepository.findById(fieldCode);
        if (field.isPresent()) {
            FieldDTO fieldDTO = mapping.convertFieldToFieldDTO(field.get());
            List<String> staffIds = new ArrayList<>();
            field.get().getStaff().forEach(
                    staff -> staffIds.add(staff.getId())
            );
            fieldDTO.setStaffId(staffIds);
            return fieldDTO;
        }else {
            return new FieldErrorResponse("Field not found", 404);
        }
    }

    @Override
    public void deleteField(String fieldCode) {
        Optional<Field> field = fieldRepository.findById(fieldCode);
        if (field.isPresent()) {
            fieldRepository.deleteById(fieldCode);
        }else {
            throw new NotFoundException("Field not found");
        }
    }

    @Override
    public List getAllFields() {
        return mapping.convertFieldListToFieldDTOList(fieldRepository.findAll());
    }

}
