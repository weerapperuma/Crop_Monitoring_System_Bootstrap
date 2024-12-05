package lk.ijse.greenshadowbackend.Repository;

import lk.ijse.greenshadowbackend.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<Field, String> {

}
