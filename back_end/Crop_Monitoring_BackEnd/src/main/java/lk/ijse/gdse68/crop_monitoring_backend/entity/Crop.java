package lk.ijse.gdse68.crop_monitoring_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "crops")
public class Crop {
    @Id
    @Column(name = "crop_code")
    private String crop_code;
    @Column(name = "crop_common_name")
    private String crop_common_name;
    @Column(name = "crop_scientific_name")
    private String scientific_name;
    @Column(name = "crop_image",columnDefinition = "LONGTEXT")
    private String crop_image;
    @Column(name = "crop_category")
    private String crop_category;
    @Column(name = "crop_season")
    private String crop_season;

    @ManyToOne
    @JoinColumn(name = "field_code",referencedColumnName = "field_code")
    private Field field;

    @ManyToMany(mappedBy = "crop")
    @JsonIgnore
    private List<CropDetails> cropDetailsList;
}
