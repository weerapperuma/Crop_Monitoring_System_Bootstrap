package lk.ijse.gdse68.crop_monitoring_backend.service;

import lk.ijse.gdse68.crop_monitoring_backend.customObj.VehicleResponse;
import lk.ijse.gdse68.crop_monitoring_backend.dto.impl.VehicleDTO;

import java.util.List;

public interface VehicleService {
    void saveVehicle(VehicleDTO vehicleDTO);

    void updateVehicle(VehicleDTO vehicleDTO , String staffId , String vehicleCode);

    VehicleResponse getVehicle(String vehicleCode);

    void deleteVehicle(String vehicleCode);

    List getAllVehicles();
}
