package lk.ijse.gdse68.crop_monitoring_backend.customObj.errorResponse;

import lk.ijse.gdse68.crop_monitoring_backend.customObj.VehicleResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleErrorResponse implements VehicleResponse {
    private int errorCode;
    private String errorMessage;
}