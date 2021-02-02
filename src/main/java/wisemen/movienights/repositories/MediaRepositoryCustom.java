package wisemen.movienights.repositories;

import wisemen.movienights.entities.Media;

import java.util.List;

public interface MediaRepositoryCustom {
    List<Media> searchMedia(String title, String type);
}
