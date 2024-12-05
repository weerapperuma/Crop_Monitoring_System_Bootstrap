package lk.ijse.greenshadowbackend.service;

import lk.ijse.greenshadowbackend.customObj.VehicleResponse;
import lk.ijse.greenshadowbackend.dto.impl.VehicleDTO;

import java.util.List;

public interface VehicleBo {
    void saveVehicle(VehicleDTO vehicleDTO);

    void updateVehicle(VehicleDTO vehicleDTO , String staffId , String vehicleCode);

    VehicleResponse getVehicle(String vehicleCode);

    void deleteVehicle(String vehicleCode);

    List getAllVehicles();
}
