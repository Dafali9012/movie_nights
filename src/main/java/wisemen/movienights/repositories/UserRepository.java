package wisemen.movienights.repositories;

import org.springframework.data.repository.CrudRepository;
import wisemen.movienights.entities.User;

public interface UserRepository extends CrudRepository<User, String> { }
