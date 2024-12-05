package lk.ijse.greenshadowbackend.service.impl;

import lk.ijse.greenshadowbackend.Repository.CropDetailsRepository;
import lk.ijse.greenshadowbackend.Repository.CropRepository;
import lk.ijse.greenshadowbackend.Repository.FieldRepository;
import lk.ijse.greenshadowbackend.Repository.StaffRepository;
import lk.ijse.greenshadowbackend.customObj.CropDetailsResponse;
import lk.ijse.greenshadowbackend.customObj.errorRespose.CropDetailsErrorResponse;
import lk.ijse.greenshadowbackend.dto.impl.CropDetailsDTO;
import lk.ijse.greenshadowbackend.entity.Crop;
import lk.ijse.greenshadowbackend.entity.CropDetails;
import lk.ijse.greenshadowbackend.entity.Field;
import lk.ijse.greenshadowbackend.entity.Staff;
import lk.ijse.greenshadowbackend.exception.DataPersistFailedException;
import lk.ijse.greenshadowbackend.exception.NotFoundException;
import lk.ijse.greenshadowbackend.service.CropDetailsBo;
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
public class CropDetailsBoIMPL implements CropDetailsBo {

    private final CropDetailsRepository cropDetailsRepository;
    private final FieldRepository fieldRepository;
    private final StaffRepository staffRepository;
    private final CropRepository cropRepository;

    private final Mapping mapping;

    @Override
    public void saveCropDetails(CropDetailsDTO cropDetailsDTO) {

        List<Field> filed = new ArrayList<>();
        List<Crop> crops = new ArrayList<>();
        List<Staff> staff = new ArrayList<>();

        for (String fieldCode : cropDetailsDTO.getFieldCodes()) {
            fieldRepository.findById(fieldCode).ifPresent(filed::add);
        }

        for (String cropCode : cropDetailsDTO.getCropCodes()) {
            cropRepository.findByCropCode(cropCode).ifPresent(crops::add);
        }

        for (String staffId : cropDetailsDTO.getStaffIds()) {
            staffRepository.findById(staffId).ifPresent(staff::add);
        }

        String logCode = AppUtil.createCropDetailsID();
        cropDetailsDTO.setLogCode(logCode);
        CropDetails cropDetails = mapping.convertCropDetailsDTOToCropDetails(cropDetailsDTO);
        cropDetails.setField(filed);
        cropDetails.setCrop(crops);
        cropDetails.setStaff(staff);
        CropDetails save = cropDetailsRepository.save(cropDetails);
        if (save == null){
            throw new DataPersistFailedException("Crop details save failed");
        }
    }

    @Override
    public void updateCropDetails(CropDetailsDTO cropDetailsDTO , String logCode) {
        Optional<CropDetails> cropDetailsByLogCode = cropDetailsRepository.findCropDetailsByLogCode(logCode);
        if (cropDetailsByLogCode.isPresent()){
            cropDetailsByLogCode.get().setLogDetails(cropDetailsDTO.getLogDetails());
            cropDetailsByLogCode.get().setObservedImage(cropDetailsDTO.getObservedImage());
        }else {
            throw new NotFoundException("Crop details not found");
        }
    }

    @Override
    public CropDetailsResponse findCropDetailsByLogCode(String logCode) {
        Optional<CropDetails> cropDetailsByLogCode = cropDetailsRepository.findCropDetailsByLogCode(logCode);
        if (cropDetailsByLogCode.isPresent()){
            CropDetailsDTO cropDetailsDTO = mapping.convertCropDetailsToCropDetailsDTO(cropDetailsByLogCode.get());
            if (cropDetailsByLogCode.get().getField() != null){
                List<String> fieldCodes = new ArrayList<>();
                cropDetailsByLogCode.get().getField().forEach(
                        field -> fieldCodes.add(field.getFieldCode())
                );
                cropDetailsDTO.setFieldCodes(fieldCodes);
            }
            if (cropDetailsByLogCode.get().getCrop() != null){
                List<String> cropCodes = new ArrayList<>();
                cropDetailsByLogCode.get().getCrop().forEach(
                        crop -> cropCodes.add(crop.getCropCode())
                );
                cropDetailsDTO.setCropCodes(cropCodes);
            }
            if (cropDetailsByLogCode.get().getStaff() != null){
                List<String> staffIds = new ArrayList<>();
                cropDetailsByLogCode.get().getStaff().forEach(
                        staff -> staffIds.add(staff.getId())
                );
                cropDetailsDTO.setStaffIds(staffIds);
            }
            return cropDetailsDTO;
        }else {
            return new CropDetailsErrorResponse(0,"Crop details not found");
        }
    }

    @Override
    public void deleteCropDetailsByLogCode(String logCode) {
        Optional<CropDetails> cropDetailsByLogCode = cropDetailsRepository.findCropDetailsByLogCode(logCode);
        if (cropDetailsByLogCode.isPresent()) {
            cropDetailsRepository.delete(cropDetailsByLogCode.get());
        }else {
            throw new NotFoundException("Crop details not found");
        }
    }

    @Override
    public List<CropDetailsDTO> getAllCropDetails() {
        List<CropDetails> all = cropDetailsRepository.findAll();
        return mapping.convertCropDetailsListToCropDetailsDTOList(all);
    }

}
