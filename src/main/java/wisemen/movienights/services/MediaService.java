package wisemen.movienights.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wisemen.movienights.entities.Media;
import wisemen.movienights.dtos.MediaResults;
import wisemen.movienights.entities.Query;
import wisemen.movienights.repositories.MediaRepository;
import wisemen.movienights.repositories.QueryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MediaService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String omdbKey = "95cca65d";

    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private QueryRepository queryRepository;

    public Media addMedia(Media newMedia) {
        mediaRepository.save(newMedia);
        return newMedia;
    }

    public List<Media> searchMediaByTitle(String query) {
        if(queryRepository.existsById(query)) {
            System.out.println("*from database*");
            return mediaRepository.findMediaByTitle(query);
        }
        queryRepository.save(new Query(query));
        List<Media> mediaResults = restTemplate.getForObject("http://www.omdbapi.com/?apikey="+omdbKey+"&s="+query, MediaResults.class).getSearch();
        for (Media media : mediaResults) {
            media.setId(Long.parseLong(media.getImdbID().substring(2)));
            if (!mediaRepository.existsById(media.getId())) mediaRepository.save(media);
        }
        System.out.println("*from omdb api*");
        return mediaResults;
    }

    public Media getMediaByID(long id) {
        Optional<Media> databaseMedia = mediaRepository.findById(id);
        if(databaseMedia.isPresent()) {
            System.out.println("*from database*");
            return databaseMedia.get();
        }

        Media foundMedia = restTemplate.getForObject("http://www.omdbapi.com/?apikey="+omdbKey+"&i=tt"+String.format("%07d",id), Media.class);
        if(foundMedia.getImdbID()==null) return null;
        else {
            foundMedia.setId(id);
            mediaRepository.save(foundMedia);
        }
        System.out.println("*from omdb api*");
        return foundMedia;
    }

    public Boolean updateMediaByID(long id, Media updatedMedia) {
        Boolean update = mediaRepository.existsById(id);
        if(update) mediaRepository.save(updatedMedia);
        return update;
    }

    public Media deleteMediaByID(long id) {
        Optional<Media> databaseMedia =  mediaRepository.findById(id);
        if(databaseMedia.isPresent()) mediaRepository.deleteById(id);
        return databaseMedia.orElse(null);
    }
}
