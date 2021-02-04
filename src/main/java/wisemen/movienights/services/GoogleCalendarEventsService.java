package wisemen.movienights.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import wisemen.movienights.entities.CustomEvent;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("deprecation")
public class   GoogleCalendarEventsService {
    private DateTime now;
    private Events events;
    private Calendar googleCalendar ;
    private GoogleCredential credentials;
    private final List<EventAttendee> attendees = new ArrayList<>();

    public ResponseEntity crateNewEvent(CustomEvent customEvent){

        Event newEvent = new Event();
        if (!customEvent.getAttendees().isEmpty()){
            setEventAttendee(customEvent);
            newEvent.setAttendees(attendees);
        }
        EventDateTime eventStart = new EventDateTime().setDateTime(new DateTime(customEvent.getStart()));
        EventDateTime eventEnd = new EventDateTime().setDateTime(new DateTime(customEvent.getEnd()) );
        newEvent.setSummary(customEvent.getSummary());
        newEvent.setStart(eventStart);
        newEvent.setEnd(eventEnd);

        try {
            googleCalendar.events().insert("primary", newEvent).execute();
            attendees.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    private void setEventAttendee(CustomEvent customEvent){
        customEvent.getAttendees().stream()
                .map( attend -> new EventAttendee().setEmail(attend))
                .forEach(attendees::add);
    }

    public ResponseEntity<List<Event>> getAllEvents(){
        now = new DateTime(System.currentTimeMillis());

        try {
            events = googleCalendar.events().list("primary")
                    .setMaxResults(20)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(events.getItems(), HttpStatus.OK);
    }

    private void initializeGoogleCredentials(String accessToken){
        credentials = new GoogleCredential().setAccessToken(accessToken);
        initializeGoogleCalendar();
    }

    private boolean initializeGoogleCalendar(){
        try {
            googleCalendar = new Calendar.Builder(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    credentials)
                    .setApplicationName("Movie Nights")
                    .build();
        } catch (Exception e){
            System.out.println("Exception ");
            e.printStackTrace();
        }
        return googleCalendar != null;
    }

    public boolean isAccessTokenValid(String accessToken) {
        Integer responseCode = null;
        try{
            String url = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token="+accessToken;
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("GET");
            responseCode = connection.getResponseCode();
        }catch (IOException e){
            e.printStackTrace();
        }
        if (responseCode == 200) initializeGoogleCredentials(accessToken);
        return responseCode == 200;
    }

    public boolean isAccessTokenValid(){
        return credentials != null;
    }
}