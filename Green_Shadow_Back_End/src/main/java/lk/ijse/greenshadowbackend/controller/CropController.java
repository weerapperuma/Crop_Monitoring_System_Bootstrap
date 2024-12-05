package lk.ijse.greenshadowbackend.controller;

import lk.ijse.greenshadowbackend.dto.impl.CropDTO;
import lk.ijse.greenshadowbackend.exception.DataPersistFailedException;
import lk.ijse.greenshadowbackend.exception.NotFoundException;
import lk.ijse.greenshadowbackend.service.CropBo;
import lk.ijse.greenshadowbackend.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/crop")
@CrossOrigin("*")
public class CropController {

    private final CropBo cropBo;
    private static final Logger logger = LoggerFactory.getLogger(CropController.class);

    @PostMapping
    public ResponseEntity<?> saveCrop(
            @RequestPart("cropName") String cropName,
            @RequestPart("cropType") String cropCategory,
            @RequestPart("cropSeason") String cropSeason,
            @RequestPart("cropScientificName") String cropScientificName,
            @RequestParam("cropImage") MultipartFile cropImage,
            @RequestParam("FieldCode") String fieldCode
    ) {
        CropDTO cropDTO = new CropDTO();
        cropDTO.setCropCommonName(cropName);
        cropDTO.setCategory(cropCategory);
        cropDTO.setCropSeason(cropSeason);
        cropDTO.setCropScientificName(cropScientificName);
        cropDTO.setCropImage(AppUtil.toBase64(cropImage));

        try {
            logger.info("Request received to save a new crop: {}", cropDTO);
            cropBo.saveCrop(cropDTO, fieldCode);
            logger.info("Crop saved successfully: {}", cropDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NotFoundException e){
            logger.error("Failed to save crop: {}", cropDTO, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataPersistFailedException e) {
            logger.error("Failed to save crop: {}", cropDTO, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Internal server error while saving crop: {}", cropDTO, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCrop(@PathVariable String id){
        try {
            logger.info("Fetching crop with ID: {}", id);
            return new ResponseEntity<>(cropBo.getCrop(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("Failed to fetch crop: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while fetching crop: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> updateCrop(
            @RequestPart("cropName") String cropName,
            @RequestPart("cropType") String cropCategory,
            @RequestPart("cropSeason") String cropSeason,
            @RequestPart("cropScientificName") String cropScientificName,
            @RequestParam("cropImage") MultipartFile cropImage,
            @RequestParam("FieldCode") String fieldCode,
            @PathVariable String id
    ) {
        CropDTO cropDTO = new CropDTO();
        cropDTO.setCropCommonName(cropName);
        cropDTO.setCategory(cropCategory);
        cropDTO.setCropSeason(cropSeason);
        cropDTO.setCropScientificName(cropScientificName);
        cropDTO.setCropImage(AppUtil.toBase64(cropImage));

        try {
            logger.info("Request received to update crop: {}", cropDTO);
            cropBo.updateCrop(cropDTO, fieldCode, id);
            logger.info("Crop updated successfully: {}", cropDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e){
            logger.error("Failed to update crop: {}", cropDTO, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataPersistFailedException e) {
            logger.error("Failed to update crop: {}", cropDTO, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Internal server error while updating crop: {}", cropDTO, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCrop(@PathVariable String id){
        try {
            logger.info("Request received to delete crop with ID: {}", id);
            cropBo.deleteCrop(id);
            logger.info("Crop deleted successfully: {}", id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("Failed to delete crop: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while deleting crop: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllCrops(){
        logger.info("Fetching all crops");
        return new ResponseEntity<>(cropBo.getAllCrops(), HttpStatus.OK);
    }

}
