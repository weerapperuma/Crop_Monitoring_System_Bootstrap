package lk.ijse.gdse68.crop_monitoring_backend.Repository;
import lk.ijse.gdse68.crop_monitoring_backend.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CropRepository extends JpaRepository<Crop, Integer> {
    Optional<Crop> findByCropCode(String id);
}
