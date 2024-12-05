package lk.ijse.gdse68.crop_monitoring_backend.Repository;

import lk.ijse.gdse68.crop_monitoring_backend.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    boolean existsByVehicleCode(String vehicleCode);

    boolean existsByLicensePlateNumber(String licensePlateNumber);

}
