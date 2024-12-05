package lk.ijse.greenshadowbackend.service.impl;

import lk.ijse.greenshadowbackend.Repository.CropRepository;
import lk.ijse.greenshadowbackend.Repository.FieldRepository;
import lk.ijse.greenshadowbackend.customObj.errorRespose.CropErrorResponse;
import lk.ijse.greenshadowbackend.customObj.CropResponse;
import lk.ijse.greenshadowbackend.dto.impl.CropDTO;
import lk.ijse.greenshadowbackend.entity.Crop;
import lk.ijse.greenshadowbackend.entity.Field;
import lk.ijse.greenshadowbackend.exception.DataPersistFailedException;
import lk.ijse.greenshadowbackend.exception.NotFoundException;
import lk.ijse.greenshadowbackend.service.CropBo;
import lk.ijse.greenshadowbackend.util.AppUtil;
import lk.ijse.greenshadowbackend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CropBoIMPL implements CropBo {

    private final CropRepository cropRepository;
    private final FieldRepository fieldRepository;
    private final Mapping mapping;

    @Override
    public void saveCrop(CropDTO cropDTO, String fieldCode) {
        cropDTO.setCropCode(AppUtil.createCropCode());
        Crop crop = mapping.convertCropDTOToCrop(cropDTO);
        Field field = fieldRepository.findById(fieldCode).orElseThrow(
                () -> new NotFoundException("Field not found")
        );
        crop.setField(field);
        Crop save = cropRepository.save(crop);
        if (save == null){
            throw new DataPersistFailedException("Crop save failed");
        }
    }

    @Override
    public CropResponse getCrop(String id) {
        Optional<Crop> byCropCode = cropRepository.findByCropCode(id);
        if (byCropCode.isPresent()){
            CropDTO cropDTO = mapping.convertCropToCropDTO(byCropCode.get());
            return cropDTO;
        }else {
            return new CropErrorResponse(0,"Crop not found");
        }
    }

    @Override
    public void updateCrop(CropDTO cropDTO, String fieldCode, String id) {
        Optional<Crop> byCropCode = cropRepository.findByCropCode(id);
        if (byCropCode.isPresent()){
            Field field = fieldRepository.findById(fieldCode).orElseThrow(
                    () -> new NotFoundException("Field not found")
            );
            byCropCode.get().setField(field);
            byCropCode.get().setCropCommonName(cropDTO.getCropCommonName());
            byCropCode.get().setCategory(cropDTO.getCategory());
            byCropCode.get().setCropSeason(cropDTO.getCropSeason());
            byCropCode.get().setCropScientificName(cropDTO.getCropScientificName());
            byCropCode.get().setCropImage(cropDTO.getCropImage());
        }else {
            throw new NotFoundException("Crop not found");
        }
    }

    @Override
    public void deleteCrop(String id) {
        Optional<Crop> byCropCode = cropRepository.findByCropCode(id);
        if (byCropCode.isPresent()){
            cropRepository.delete(byCropCode.get());
        }else {
            throw new NotFoundException("Crop not found");
        }
    }

    @Override
    public List getAllCrops() {
        return mapping.convertCropListToCropDTOList(cropRepository.findAll());
    }
}
