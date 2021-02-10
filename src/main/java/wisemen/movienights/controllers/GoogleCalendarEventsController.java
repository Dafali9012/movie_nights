package wisemen.movienights.controllers;

import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wisemen.movienights.entities.CustomEvent;
import wisemen.movienights.services.GoogleCalendarEventsService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/rest/v1/calendar/events")
public class GoogleCalendarEventsController {

    @Autowired
    GoogleCalendarEventsService calendarEventsService;

    @GetMapping("/{accessToken}")
    public ResponseEntity initializeAccessToken(@PathVariable String accessToken) {
        if (calendarEventsService.isAccessTokenValid(accessToken)){
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else return new ResponseEntity("Invalid access_token", HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(){
        if (calendarEventsService.isAccessTokenValid()) return calendarEventsService.getAllEvents();
        else return new ResponseEntity("access_token is missing",HttpStatus.FAILED_DEPENDENCY);
    }

    @PostMapping
    public ResponseEntity createGoogleCalendarEvent(@RequestBody CustomEvent event) {
       if (calendarEventsService.isAccessTokenValid()) calendarEventsService.crateNewEvent(event);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }


}