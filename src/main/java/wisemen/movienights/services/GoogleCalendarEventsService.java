package wisemen.movienights.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import wisemen.movienights.entities.CustomEvent;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
@SuppressWarnings("deprecation")
public class GoogleCalendarEventsService {
    private DateTime now;
    private Events events;
    private  Calendar googleCalendar ;
    private List<Event> calendarEvents;
    private GoogleCredential credentials;


    public ResponseEntity crateNewEvent(CustomEvent customEvent){
//        System.out.println("NEW EVENT "+ customEvent.toString());
//
        Event newEvent = new Event();
        EventDateTime eventStart = new EventDateTime().setDateTime(new DateTime(System.currentTimeMillis()));
        EventDateTime eventEnd = new EventDateTime().setDateTime(new DateTime(customEvent.getEnd().getValue()) );


//        System.out.println("eventStart :"+ eventStart.toString());
//        newEvent.setSummary(customEvent.getSummary());
//        newEvent.setStart(eventStart);
//        newEvent.setEnd(eventEnd);


//        EventDateTime eventStart = new EventDateTime().setDateTime(new DateTime(System.currentTimeMillis()));
//        EventDateTime eventEnd = new EventDateTime().setDateTime(new DateTime(System.currentTimeMillis() + (1000 * 60 * 60 * 2)));


        newEvent.setSummary("New movie! Batflex");

        newEvent.setStart(eventStart);

        newEvent.setEnd(eventEnd);

        try {
            googleCalendar.events().insert("primary", newEvent).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }



    public ResponseEntity<List<Event>> getAllEvents(){
        now = new DateTime(System.currentTimeMillis());
//        try {
//            googleCalendar = new Calendar.Builder(
//                    new NetHttpTransport(),
//                    JacksonFactory.getDefaultInstance(),
//                    credentials)
//                    .setApplicationName("Movie Nights")
//                    .build();
//        } catch (Exception e){
//            System.out.println("Exception ");
//            e.printStackTrace();
//        }

        try {
            events = googleCalendar.events().list("primary")
                    .setMaxResults(10)
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


    public boolean isAccessTokenValid(){
        return credentials != null;
    }
}