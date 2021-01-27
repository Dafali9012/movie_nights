package wisemen.movienights.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wisemen.movienights.entities.Media;
import wisemen.movienights.services.MediaService;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/media")
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @GetMapping("/{query}")
    public List<Media> findMediaByTitle() {
        List<Media> foundMedia = mediaService.findMediaByTitle();
//        if(foundMedia.isEmpty())
        return null;
    }
}
