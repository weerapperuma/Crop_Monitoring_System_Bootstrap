package lk.ijse.gdse68.crop_monitoring_backend.controller;

import lk.ijse.gdse68.crop_monitoring_backend.customObj.CropDetailsResponse;
import lk.ijse.gdse68.crop_monitoring_backend.dto.impl.CropDetailsDTO;
import lk.ijse.gdse68.crop_monitoring_backend.exception.DataPersistFailedException;
import lk.ijse.gdse68.crop_monitoring_backend.exception.NotFoundException;
import lk.ijse.gdse68.crop_monitoring_backend.service.CropDetailsService;
import lk.ijse.gdse68.crop_monitoring_backend.util.AppUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cropDetails")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CropDetailsController {

    private final CropDetailsService cropDetailsService;

    @PostMapping
    public ResponseEntity<?> saveCropDetails(
            @RequestPart(value = "logDetails") String logDetails,
            @RequestPart(value = "observedImg") MultipartFile observedImg,
            @RequestParam(value = "fieldCode") List<String> fieldCodes,
            @RequestParam(value = "cropCode") List<String> cropCodes,
            @RequestParam(value = "staffId") List<String> staffIds
    ) {
        log.info("Attempting to save crop details with fieldCodes: {}, cropCodes: {}, staffIds: {}", fieldCodes, cropCodes, staffIds);
        try {
            String updateBase64ProfilePic = AppUtil.toBase64(observedImg);
            CropDetailsDTO cropDetailsDTO = new CropDetailsDTO();
            cropDetailsDTO.setLogDate(new Date());
            cropDetailsDTO.setLogDetails(logDetails);
            cropDetailsDTO.setObservedImage(updateBase64ProfilePic);
            cropDetailsDTO.setFieldCodes(fieldCodes);
            cropDetailsDTO.setCropCodes(cropCodes);
            cropDetailsDTO.setStaffIds(staffIds);
            cropDetailsService.saveCropDetails(cropDetailsDTO);
            log.info("Crop details saved successfully.");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            log.error("Failed to persist crop details.", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(value = "/{logCode}")
    public ResponseEntity<?> updateCropDetails(
            @RequestPart(value = "logDetails") String logDetails,
            @RequestPart(value = "observedImg") MultipartFile observedImg,
            @PathVariable(value = "logCode") String logCode
    ) {
        log.info("Attempting to update crop details for logCode: {}", logCode);
        try {
            String updateBase64ProfilePic = AppUtil.toBase64(observedImg);
            CropDetailsDTO cropDetailsDTO = new CropDetailsDTO();
            cropDetailsDTO.setLogDetails(logDetails);
            cropDetailsDTO.setObservedImage(updateBase64ProfilePic);
            cropDetailsService.updateCropDetails(cropDetailsDTO, logCode);
            log.info("Crop details updated successfully for logCode: {}", logCode);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            log.warn("Crop details not found for logCode: {}", logCode);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{logCode}")
    public ResponseEntity<?> findCropDetailsByLogCode(@PathVariable String logCode) {
        log.info("Fetching crop details for logCode: {}", logCode);
        try {
            CropDetailsResponse cropDetailsByLogCode = cropDetailsService.findCropDetailsByLogCode(logCode);
            log.info("Crop details fetched successfully for logCode: {}", logCode);
            return new ResponseEntity<>(cropDetailsByLogCode, HttpStatus.OK);
        } catch (NotFoundException e) {
            log.warn("Crop details not found for logCode: {}", logCode);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{logCode}")
    public ResponseEntity<?> deleteCropDetailsByLogCode(@PathVariable String logCode) {
        log.info("Attempting to delete crop details for logCode: {}", logCode);
        try {
            cropDetailsService.deleteCropDetailsByLogCode(logCode);
            log.info("Crop details deleted successfully for logCode: {}", logCode);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            log.warn("Crop details not found for logCode: {}", logCode);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllCropDetails() {
        log.info("Fetching all crop details.");
        return new ResponseEntity<>(cropDetailsService.getAllCropDetails(), HttpStatus.OK);
    }

}