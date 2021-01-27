package wisemen.movienights.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wisemen.movienights.entities.Media;
import wisemen.movienights.services.MediaService;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/media")
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Media>> findMediaByTitle(@RequestParam(name = "title") String query) {
        List<Media> foundMedia = mediaService.findMediaByTitle(query);
        return ResponseEntity.ok(foundMedia);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Media> getMediaByID(@PathVariable String id) {
        Media foundMedia = mediaService.getMediaByID(id);
        if(foundMedia==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(foundMedia);
    }
}
