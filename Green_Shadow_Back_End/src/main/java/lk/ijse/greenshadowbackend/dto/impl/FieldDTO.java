package lk.ijse.greenshadowbackend.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;
import lk.ijse.greenshadowbackend.customObj.FieldResponse;
import lk.ijse.greenshadowbackend.dto.SuperDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldDTO implements FieldResponse , SuperDto {

    private String fieldCode;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9 ]+$")
    private String fieldName;

    @NotBlank
    private Point fieldLocation;

    @Positive
    @NotNull // Changed to @NotNull to avoid conflicts
    private double fieldSize;

    @NotNull
    private String image1;

    @NotNull
    private String image2;

    @NotNull
    private List<String> staffId;
}
