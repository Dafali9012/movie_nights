package wisemen.movienights.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wisemen.movienights.entities.User;

public interface UserRepository  extends JpaRepository<User, Integer> {
    User findById(int id);
    User findByEmail(String email);
}
