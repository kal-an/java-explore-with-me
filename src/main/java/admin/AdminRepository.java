package admin;

import org.springframework.data.jpa.repository.JpaRepository;
import user.model.User;

public interface AdminRepository extends JpaRepository<User, Integer> {

}
