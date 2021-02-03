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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Media> searchMedia(String type, String title) {
        Optional<Query> databaseQuery = queryRepository.findById(type+title);
        if(databaseQuery.isPresent()) return mediaRepository.searchMedia(type, title);
        else queryRepository.save(new Query(type+title));

        MediaResults results = restTemplate.getForObject("http://www.omdbapi.com/?apikey="+omdbKey+"&type="+type+"&s="+title, MediaResults.class);
        List<Media> mediaList;
        if(results.getError()!=null) return mediaRepository.searchMedia(type, title);
        mediaList = results.getSearch();
        mediaList = mediaList.stream().map(a-> findMediaByID(Long.parseLong(a.getImdbID().substring(2)))).collect(Collectors.toList());

        return mediaList;
    }

    public Media findMediaByID(long id) {
        Optional<Media> databaseMedia = mediaRepository.findById(id);
        if(databaseMedia.isPresent()) {
            System.out.println("*from database*");
            return databaseMedia.get();
        }

        Media media = restTemplate.getForObject("http://www.omdbapi.com/?apikey="+omdbKey+"&i=tt"+String.format("%07d",id), Media.class);
        if(media.getImdbID()==null) return null;
        else {
            media.setId(Long.parseLong(media.getImdbID().substring(2)));
            mediaRepository.save(media);
        }
        System.out.println("*from omdb api*");
        return media;
    }

    public Media updateMediaByID(long id) {
        return restTemplate.getForObject("http://www.omdbapi.com/?apikey="+omdbKey+"&i=tt"+String.format("%07d",id), Media.class);
    }

    public void deleteMedia() {
        mediaRepository.deleteAll();
        queryRepository.deleteAll();
    }
}
