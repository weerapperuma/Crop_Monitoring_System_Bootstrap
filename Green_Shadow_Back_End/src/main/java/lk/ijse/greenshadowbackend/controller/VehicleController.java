package lk.ijse.greenshadowbackend.controller;

import jakarta.validation.Valid;
import lk.ijse.greenshadowbackend.dto.impl.VehicleDTO;
import lk.ijse.greenshadowbackend.exception.AlreadyExistsException;
import lk.ijse.greenshadowbackend.exception.DataPersistFailedException;
import lk.ijse.greenshadowbackend.exception.NotFoundException;
import lk.ijse.greenshadowbackend.service.VehicleBo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicle")
@RequiredArgsConstructor
@CrossOrigin("*")
public class VehicleController {

    private final VehicleBo vehicleBo;
    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @PostMapping
    public ResponseEntity<?> saveVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) {
        try {
            logger.info("Attempting to save vehicle: {}", vehicleDTO);
            vehicleBo.saveVehicle(vehicleDTO);
            logger.info("Vehicle saved successfully: {}", vehicleDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (AlreadyExistsException e) {
            logger.error("Vehicle already exists: {}", vehicleDTO, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (DataPersistFailedException e) {
            logger.error("Failed to persist vehicle data: {}", vehicleDTO, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An error occurred while saving the vehicle: {}", vehicleDTO, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/{vehicleCode}", params = "staffId")
    public ResponseEntity<?> updateVehicle(@Valid @RequestBody VehicleDTO vehicleDTO , @RequestParam("staffId") String staffId , @PathVariable("vehicleCode") String vehicleCode) {
        try {
            logger.info("Attempting to update vehicle: {}", vehicleDTO);
            vehicleBo.updateVehicle(vehicleDTO, staffId, vehicleCode);
            logger.info("Vehicle updated successfully: {}", vehicleDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("Failed to update vehicle: {}", vehicleDTO, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (DataPersistFailedException e) {
            logger.error("Failed to persist vehicle data: {}", vehicleDTO, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An error occurred while updating the vehicle: {}", vehicleDTO, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{vehicleCode}")
    public ResponseEntity<?> getVehicle(@PathVariable String vehicleCode) {
        try {
            logger.info("Attempting to get vehicle by vehicle code: {}", vehicleCode);
            return new ResponseEntity<>(vehicleBo.getVehicle(vehicleCode), HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("Failed to get vehicle by vehicle code: {}", vehicleCode, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An error occurred while getting the vehicle by vehicle code: {}", vehicleCode, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{vehicleCode}")
    public ResponseEntity<?> deleteVehicle(@PathVariable String vehicleCode) {
        try {
            logger.info("Attempting to delete vehicle by vehicle code: {}", vehicleCode);
            vehicleBo.deleteVehicle(vehicleCode);
            logger.info("Vehicle deleted successfully: {}", vehicleCode);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("Failed to delete vehicle by vehicle code: {}", vehicleCode, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An error occurred while deleting the vehicle by vehicle code: {}", vehicleCode, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllVehicles() {
        try {
            logger.info("Attempting to get all vehicles");
            return new ResponseEntity<>(vehicleBo.getAllVehicles(), HttpStatus.OK);
        } catch (NotFoundException e) {
            logger.error("Failed to get all vehicles", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An error occurred while getting all vehicles", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
