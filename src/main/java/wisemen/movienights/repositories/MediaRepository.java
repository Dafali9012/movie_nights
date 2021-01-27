package wisemen.movienights.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wisemen.movienights.entities.Media;

@Repository
public interface MediaRepository extends CrudRepository<Media, Long> {}
