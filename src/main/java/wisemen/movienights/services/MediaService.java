package wisemen.movienights.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wisemen.movienights.entities.Media;
import wisemen.movienights.models.MediaResults;
import wisemen.movienights.repositories.MediaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MediaService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String omdbKey = "95cca65d";

    @Autowired
    private MediaRepository mediaRepository;

    public List<Media> findMediaByTitle(String query) {
        List<Media> foundMedia = restTemplate.getForObject("http://www.omdbapi.com/?apikey="+omdbKey+"&s="+query, MediaResults.class).getSearch();
        for(int i = 0; i < foundMedia.size(); i++) {
            foundMedia.get(i).setId(Long.parseLong(foundMedia.get(i).getImdbID().substring(2)));
            if(!mediaRepository.existsById(foundMedia.get(i).getId())) mediaRepository.save(foundMedia.get(i));
        }
        return foundMedia;
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

    public Boolean deleteMediaByID(long id) {
        if(mediaRepository.existsById(id)) {
            mediaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
