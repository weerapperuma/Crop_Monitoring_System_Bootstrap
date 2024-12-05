package lk.ijse.gdse68.crop_monitoring_backend.Repository;

import lk.ijse.gdse68.crop_monitoring_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
