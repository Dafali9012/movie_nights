package wisemen.movienights.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wisemen.movienights.entities.Media;
import wisemen.movienights.services.MediaService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/v1/media")
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Media>> searchMedia(@RequestParam String type, @RequestParam String title) {
        List<Media> mediaList = mediaService.searchMedia(type, title);
        return ResponseEntity.ok(mediaList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Media> findMediaByID(@PathVariable long id) {
        Media media = mediaService.findMediaByID(id);
        if(media==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(media, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<Media> addMedia(@RequestBody Media newMedia) {
        return new ResponseEntity<>(mediaService.addMedia(newMedia), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Media> updateMediaByID(@PathVariable long id) {
        Media media = mediaService.updateMediaByID(id);
        if(media==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(media, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Media> deleteMedia() {
        mediaService.deleteMedia();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
