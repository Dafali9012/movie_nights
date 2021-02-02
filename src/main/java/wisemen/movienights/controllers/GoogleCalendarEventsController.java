package wisemen.movienights.controllers;

import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wisemen.movienights.services.GoogleCalendarEventsService;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/calendar/events")
public class GoogleCalendarEventsController {

    @Autowired
    GoogleCalendarEventsService eventsService;

    @GetMapping("/{accessToken}")
    public ResponseEntity initializeAccessToken(@PathVariable String accessToken) {
        if (eventsService.isAccessTokenValid(accessToken)){
            return eventsService.initializeGoogleCalendar(accessToken);
        } else return new ResponseEntity("Invalid access_token", HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping
    public List<Event> getAllEvents(){return eventsService.getAllEvents();}

}