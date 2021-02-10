package wisemen.movienights.repositories;

import org.springframework.data.repository.CrudRepository;
import wisemen.movienights.entities.Media;

public interface MediaRepository extends CrudRepository<Media, Long>, MediaRepositoryCustom {}
