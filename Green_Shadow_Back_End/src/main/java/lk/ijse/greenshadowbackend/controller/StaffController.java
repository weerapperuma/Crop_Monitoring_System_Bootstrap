package lk.ijse.greenshadowbackend.controller;

import jakarta.validation.Valid;
import lk.ijse.greenshadowbackend.customObj.StaffResponse;
import lk.ijse.greenshadowbackend.dto.impl.StaffDTO;
import lk.ijse.greenshadowbackend.exception.AlreadyExistsException;
import lk.ijse.greenshadowbackend.exception.DataPersistFailedException;
import lk.ijse.greenshadowbackend.exception.NotFoundException;
import lk.ijse.greenshadowbackend.service.StaffBo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/staff")
@CrossOrigin("*")
public class StaffController {

    private static final Logger logger = LoggerFactory.getLogger(StaffController.class);
    private final StaffBo staffBo;

    @PostMapping
    public ResponseEntity<?> saveStaff(@Valid @RequestBody StaffDTO staffDTO){
        logger.info("Attempting to save staff: {}", staffDTO);
        try {
            staffBo.saveStaff(staffDTO);
            logger.info("Successfully saved staff with ID: {}", staffDTO.getId());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistFailedException e) {
            logger.error("Failed to save staff: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (AlreadyExistsException e){
          logger.error("Email already existed");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while saving staff: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffResponse> getStaff(@PathVariable String id){
        logger.info("Fetching staff with ID: {}", id);
        try {
            StaffResponse staffResponse = staffBo.getStaff(id);
            logger.info("Successfully fetched staff with ID: {}", id);
            return new ResponseEntity<>(staffResponse, HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("Failed to fetch staff: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while fetching staff: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> updateStaff(@Valid @RequestBody StaffDTO staffDTO , @PathVariable("id") String id){
        logger.info("Attempting to update staff: {}", staffDTO);
        try {
            staffBo.updateStaff(staffDTO,id);
            logger.info("Successfully updated staff with ID: {}", staffDTO.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException | DataPersistFailedException e) {
            logger.error("Failed to update staff: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while updating staff: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStaff(@PathVariable String id){
        logger.info("Attempting to delete staff with ID: {}", id);
        try {
            staffBo.deleteStaff(id);
            logger.info("Successfully deleted staff with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("Failed to delete staff: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while deleting staff: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllStaff(){
        logger.info("Fetching all staff members");
        return new ResponseEntity<>(staffBo.getAllStaff(), HttpStatus.OK);
    }
}
