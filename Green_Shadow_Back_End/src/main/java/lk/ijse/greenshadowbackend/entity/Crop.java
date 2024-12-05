package lk.ijse.greenshadowbackend.entity;

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
    private String cropCode;
    @Column(name = "crop_common_name")
    private String cropCommonName;
    @Column(name = "crop_scientific_name")
    private String cropScientificName;
    @Column(name = "crop_image", columnDefinition = "LONGTEXT")
    private String cropImage;
    @Column(name = "category")
    private String category;
    @Column(name = "crop_season")
    private String cropSeason;

    @ManyToOne
    @JoinColumn(name = "field_code", referencedColumnName = "field_code")
    private Field field;

    @ManyToMany(mappedBy = "crop" , cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CropDetails> cropDetails;
}