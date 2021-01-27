package wisemen.movienights.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wisemen.movienights.entities.Media;
import wisemen.movienights.models.MediaResults;

import java.util.ArrayList;
import java.util.List;

@Service
public class MediaService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String omdbKey = "95cca65d";

    public List<Media> findMediaByTitle() {
        List<Media> foundMedia = restTemplate.getForObject("", MediaResults.class).getSearch();
        if(foundMedia==null) return new ArrayList<>();
        return foundMedia;
    }
}
