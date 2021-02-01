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

    @PostMapping
    public ResponseEntity<Media> addMedia(@RequestBody Media newMedia) {
        return new ResponseEntity<>(mediaService.addMedia(newMedia), HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Media>> searchMediaByTitle(@RequestParam(name = "title") String query) {
        List<Media> mediaResults = mediaService.searchMediaByTitle(query);
        return ResponseEntity.ok(mediaResults);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Media> getMediaByID(@PathVariable long id) {
        Media foundMedia = mediaService.getMediaByID(id);
        if(foundMedia==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(foundMedia);
    }

    // in progress
    @PutMapping("/{id}")
    public ResponseEntity<Media> updateMediaByID(@PathVariable long id, @RequestBody Media updatedMedia) {
        Boolean updated = mediaService.updateMediaByID(id, updatedMedia);
        if(!updated) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(updatedMedia, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Media> deleteMediaByID(@PathVariable long id) {
        Media foundMedia = mediaService.deleteMediaByID(id);
        if(foundMedia!=null) return new ResponseEntity<>(foundMedia, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
