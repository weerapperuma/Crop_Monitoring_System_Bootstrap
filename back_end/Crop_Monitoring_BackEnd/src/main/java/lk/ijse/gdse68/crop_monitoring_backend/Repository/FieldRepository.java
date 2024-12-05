package lk.ijse.gdse68.crop_monitoring_backend.Repository;

import lk.ijse.gdse68.crop_monitoring_backend.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<Field, String> {

}
