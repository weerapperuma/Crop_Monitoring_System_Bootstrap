package lk.ijse.gdse68.crop_monitoring_backend.customObj.errorResponse;

import lk.ijse.gdse68.crop_monitoring_backend.customObj.EquipmentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentErrorResponse implements EquipmentResponse , Serializable {
    private String message;
    private int errorCode;
}
