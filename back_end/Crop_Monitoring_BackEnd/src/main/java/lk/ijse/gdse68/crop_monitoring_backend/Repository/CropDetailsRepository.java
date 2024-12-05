package lk.ijse.gdse68.crop_monitoring_backend.Repository;

import lk.ijse.gdse68.crop_monitoring_backend.entity.CropDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface CropDetailsRepository extends JpaRepository<CropDetails, String> {
    boolean existsByLogCode(String logCode);
    Optional<CropDetails> findCropDetailsByLogCode(String logCode);
}
