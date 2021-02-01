package wisemen.movienights.repositories.implementations;

import org.springframework.stereotype.Repository;
import wisemen.movienights.entities.Media;
import wisemen.movienights.repositories.MediaRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class MediaRepositoryImpl implements MediaRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Media> findMediaByTitle(String title) {
        Query query = entityManager.createNativeQuery("select * from media where title like '%"+title+"%'", Media.class);
        return query.getResultList();
    }
}
