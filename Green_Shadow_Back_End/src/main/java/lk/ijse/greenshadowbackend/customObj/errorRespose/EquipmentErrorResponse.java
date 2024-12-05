package lk.ijse.greenshadowbackend.customObj.errorRespose;

import lk.ijse.greenshadowbackend.customObj.EquipmentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentErrorResponse implements EquipmentResponse, Serializable {
    private String message;
    private int errorCode;
}
