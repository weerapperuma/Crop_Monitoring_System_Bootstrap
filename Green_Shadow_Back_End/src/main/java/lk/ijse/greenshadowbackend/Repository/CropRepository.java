package lk.ijse.greenshadowbackend.Repository;

import lk.ijse.greenshadowbackend.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CropRepository extends JpaRepository<Crop, Integer> {
    Optional<Crop> findByCropCode(String id);
}
