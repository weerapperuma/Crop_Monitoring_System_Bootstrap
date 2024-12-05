package lk.ijse.gdse68.crop_monitoring_backend.service.impl;

import lk.ijse.gdse68.crop_monitoring_backend.Repository.CropDetailsRepository;
import lk.ijse.gdse68.crop_monitoring_backend.Repository.CropRepository;
import lk.ijse.gdse68.crop_monitoring_backend.Repository.FieldRepository;
import lk.ijse.gdse68.crop_monitoring_backend.Repository.StaffRepository;
import lk.ijse.gdse68.crop_monitoring_backend.customObj.CropDetailsResponse;
import lk.ijse.gdse68.crop_monitoring_backend.customObj.errorResponse.CropDetailsErrorResponse;
import lk.ijse.gdse68.crop_monitoring_backend.dto.impl.CropDetailsDTO;
import lk.ijse.gdse68.crop_monitoring_backend.entity.Crop;
import lk.ijse.gdse68.crop_monitoring_backend.entity.CropDetails;
import lk.ijse.gdse68.crop_monitoring_backend.entity.Field;
import lk.ijse.gdse68.crop_monitoring_backend.entity.Staff;
import lk.ijse.gdse68.crop_monitoring_backend.exception.DataPersistFailedException;
import lk.ijse.gdse68.crop_monitoring_backend.exception.NotFoundException;
import lk.ijse.gdse68.crop_monitoring_backend.service.CropDetailsService;
import lk.ijse.gdse68.crop_monitoring_backend.util.AppUtil;
import lk.ijse.gdse68.crop_monitoring_backend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CropDetailsServiceIMPL implements CropDetailsService {

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
