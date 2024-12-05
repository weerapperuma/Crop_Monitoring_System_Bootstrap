package lk.ijse.greenshadowbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "crop_details")
public class CropDetails {
    @Id
    @Column(name = "log_code")
    private String logCode;
    private Date logDate;
    private String logDetails;
    @Column(columnDefinition = "LONGTEXT")
    private String observedImage;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "field_log",
            joinColumns = @JoinColumn(name = "field_code"),
            inverseJoinColumns = @JoinColumn(name = "log_code")
    )
    private List<Field> field;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "log_crop_details",
            joinColumns = @JoinColumn(name = "log_code"),
            inverseJoinColumns = @JoinColumn(name = "crop_code")
    )
    private List<Crop> crop;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "log_staff",
            joinColumns = @JoinColumn(name = "log_code"),
            inverseJoinColumns = @JoinColumn(name = "staff_member_id")
    )
    private List<Staff> staff;
}