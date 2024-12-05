package lk.ijse.greenshadowbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "equipment")
public class Equipment {
    @Id
    @Column(name = "eqiupment_id")
    private String equipmentId;
    @Column(name = "equipment_name")
    private String equipmentName;
    @Column(name = "equipment_type")
    private String equipmentType;
    @Column(name = "availability_status")
    private String status;

    @ManyToOne(optional = true)
    @JoinColumn(name = "field_code", referencedColumnName = "field_code")
    private Field field;

    @OneToOne(optional = true)
    @JoinColumn(name = "staff_member_id", referencedColumnName = "staff_member_id")
    private Staff staff;
}