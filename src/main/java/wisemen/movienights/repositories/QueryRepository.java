package wisemen.movienights.repositories;

import org.springframework.data.repository.CrudRepository;
import wisemen.movienights.entities.Query;

public interface QueryRepository extends CrudRepository<Query, String> {}
