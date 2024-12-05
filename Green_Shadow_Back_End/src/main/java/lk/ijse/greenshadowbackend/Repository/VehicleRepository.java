package lk.ijse.greenshadowbackend.Repository;

import lk.ijse.greenshadowbackend.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    boolean existsByVehicleCode(String vehicleCode);

    boolean existsByLicensePlateNumber(String licensePlateNumber);

}
