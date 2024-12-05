package lk.ijse.greenshadowbackend.customObj.errorRespose;

import lk.ijse.greenshadowbackend.customObj.StaffResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffErrorResponse implements StaffResponse, Serializable {
    private int status;
    private String message;
}
