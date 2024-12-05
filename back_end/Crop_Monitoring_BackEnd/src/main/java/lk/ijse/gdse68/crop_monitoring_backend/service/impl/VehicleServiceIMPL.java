package lk.ijse.gdse68.crop_monitoring_backend.service.impl;

import lk.ijse.gdse68.crop_monitoring_backend.Repository.StaffRepository;
import lk.ijse.gdse68.crop_monitoring_backend.Repository.VehicleRepository;
import lk.ijse.gdse68.crop_monitoring_backend.customObj.VehicleResponse;
import lk.ijse.gdse68.crop_monitoring_backend.customObj.errorResponse.VehicleErrorResponse;
import lk.ijse.gdse68.crop_monitoring_backend.dto.impl.VehicleDTO;
import lk.ijse.gdse68.crop_monitoring_backend.entity.Staff;
import lk.ijse.gdse68.crop_monitoring_backend.entity.Vehicle;
import lk.ijse.gdse68.crop_monitoring_backend.exception.AlreadyExistsException;
import lk.ijse.gdse68.crop_monitoring_backend.exception.DataPersistFailedException;
import lk.ijse.gdse68.crop_monitoring_backend.exception.NotFoundException;
import lk.ijse.gdse68.crop_monitoring_backend.service.VehicleService;
import lk.ijse.gdse68.crop_monitoring_backend.util.AppUtil;
import lk.ijse.gdse68.crop_monitoring_backend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VehicleServiceIMPL implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final Mapping mapping;
    private final StaffRepository staffRepository;

    @Override
    public void saveVehicle(VehicleDTO vehicleDTO){
        String vehicleCode = AppUtil.createVehicleCode();
        vehicleDTO.setVehicleCode(vehicleCode);
        if (vehicleRepository.existsByLicensePlateNumber(vehicleDTO.getLicensePlateNumber())){
            throw new AlreadyExistsException("vehicle plate number already used");
        }else {
            Vehicle save = vehicleRepository.save(mapping.convertVehicleDTOToVehicle(vehicleDTO));
            if (save == null){
                throw new DataPersistFailedException("vehicle save failed");
            }
        }
    }

    @Override
    public void updateVehicle(VehicleDTO vehicleDTO, String staffId , String vehicleCode) {

        Vehicle vehicle = vehicleRepository.findById(vehicleCode)
                .orElseThrow(() -> new NotFoundException("Vehicle not found"));

        Staff staff = null;
        if (!staffId.equals("N/A")) {

            staff = staffRepository.findById(staffId)
                    .orElseThrow(() -> new NotFoundException("Staff not found"));
            vehicle.setStaff(staff);
        }

        vehicle.setLicensePlateNumber(vehicleDTO.getLicensePlateNumber());
        vehicle.setVehicleCategory(vehicleDTO.getVehicleCategory());
        vehicle.setFuelType(vehicleDTO.getFuelType());
        vehicle.setStatus(vehicleDTO.getStatus());
        vehicle.setRemarks(vehicleDTO.getRemarks());

        if (staff != null) {
            vehicle.setStaff(staff);
        } else {
            vehicle.setStaff(null);
        }

        vehicleRepository.save(vehicle);
    }


    @Override
    public VehicleResponse getVehicle(String vehicleCode) {
        Optional<Vehicle> byId = vehicleRepository.findById(vehicleCode);
        if (byId.isPresent()){
            return mapping.convertVehicleToVehicleDTO(byId.get());
        }else {
            return new VehicleErrorResponse(404 , "vehicle not found");
        }
    }

    @Override
    public void deleteVehicle(String vehicleCode) {
        Vehicle vehicle = vehicleRepository.findById(vehicleCode)
                .orElseThrow(() -> new NotFoundException("vehicle not found"));
        vehicleRepository.delete(vehicle);
    }

    @Override
    public List getAllVehicles() {
        return mapping.convertVehicleListToVehicleDTOList(vehicleRepository.findAll());
    }


}
