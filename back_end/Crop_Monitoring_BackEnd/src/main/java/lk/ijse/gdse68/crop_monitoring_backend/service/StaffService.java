package lk.ijse.gdse68.crop_monitoring_backend.service;

import jakarta.validation.Valid;
import lk.ijse.gdse68.crop_monitoring_backend.customObj.StaffResponse;
import lk.ijse.gdse68.crop_monitoring_backend.dto.impl.StaffDTO;

import java.util.List;

public interface StaffService {
    void saveStaff(StaffDTO staffDTO);

    StaffResponse getStaff(String id);

    void updateStaff(@Valid StaffDTO staffDTO,String id);

    void deleteStaff(String id);

    List<StaffDTO> getAllStaff();
}
