package lk.ijse.gdse68.crop_monitoring_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lk.ijse.gdse68.crop_monitoring_backend.util.Enum.Gender;
import lk.ijse.gdse68.crop_monitoring_backend.util.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "staff")
public class Staff {
    @Id
    @Column(name = "staff_member_id")
    private String id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "designation")
    private String designation;
    @Column(name = "Gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "joined_date")
    private Date joinedDate;
    @Column(name = "date_of_birth")
    private Date DOB;
    @Column(name = "address_line_1")
    private String addressLine1;
    @Column(name = "address_line_2")
    private String addressLine2;
    @Column(name = "address_line_3")
    private String addressLine3;
    @Column(name = "address_line_4")
    private String addressLine4;
    @Column(name = "address_line_5")
    private String addressLine5;
    @Column(name = "contact_no")
    private String contactNo;
    @Column(name = "email" , unique = true)
    private String email;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "staff")
    @JsonIgnore
    private List<Field> field;

    @ManyToMany(mappedBy = "staff")
    @JsonIgnore
    private List<CropDetails> cropDetails;

    @OneToOne(mappedBy = "staff",optional = true)
    @JsonIgnore
    private Equipment equipment;

    @OneToMany(mappedBy = "staff")
    @JsonIgnore
    private List<Vehicle> vehicles;


}
