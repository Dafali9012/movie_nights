package wisemen.movienights.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import wisemen.movienights.entities.CustomEvent;
import wisemen.movienights.entities.User;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("deprecation")
public class GoogleCalendarEventsService {
    private DateTime now;
    private Events events;

    private String CLIENT_ID = "834224170973-rafg4gcu10p2dbjk594ntg8696ucq06q.apps.googleusercontent.com";
    private String CLIENT_SECRET = "8qMXbutui-w-ygkf7UfBIjw0";

    @Autowired
    private UserService userService;

    public ResponseEntity createNewEvent(CustomEvent customEvent){
        List<EventAttendee> attendees = new ArrayList<>();
        customEvent.getAttendees().forEach(email->{
            attendees.add(new EventAttendee().setEmail(email));
        });

        Event newEvent = new Event();
        newEvent.setAttendees(attendees);
        EventDateTime eventStart = new EventDateTime().setDateTime(new DateTime(customEvent.getStart()));
        EventDateTime eventEnd = new EventDateTime().setDateTime(new DateTime(customEvent.getEnd()));
        newEvent.setSummary(customEvent.getSummary());
        newEvent.setDescription(customEvent.getDescription());
        newEvent.setStart(eventStart);
        newEvent.setEnd(eventEnd);
        newEvent.setLocation(customEvent.getLocation());

        customEvent.getAttendees().forEach(email->{
            User user = userService.getUser(email);

            GoogleCredential credentials = getRefreshedCredentials(user.getRefreshToken());
            Calendar googleCalendar;

            try {
                googleCalendar = new Calendar.Builder(
                        new NetHttpTransport(),
                        JacksonFactory.getDefaultInstance(),
                        credentials)
                        .setApplicationName("Movie Nights")
                        .build();

                googleCalendar.events().insert("primary", newEvent).execute();
            } catch (Exception e){
                System.out.println("Exception ");
                e.printStackTrace();
            }
        });
        attendees.clear();
        return new ResponseEntity(HttpStatus.OK);
    }

    /*
    private void setEventAttendee(CustomEvent customEvent){
        customEvent.getAttendees().stream()
                .map( attend -> new EventAttendee().setEmail(attend))
                .forEach(attendees::add);
    }
     */

    public GoogleCredential getRefreshedCredentials(String refreshCode) {
        try {
            GoogleTokenResponse response = new GoogleRefreshTokenRequest(
                    new NetHttpTransport(), JacksonFactory.getDefaultInstance(), refreshCode, CLIENT_ID, CLIENT_SECRET)
                    .execute();

            return new GoogleCredential().setAccessToken(response.getAccessToken());
        }
        catch( Exception ex ){
            ex.printStackTrace();
            return null;
        }
    }

    /*
    public ResponseEntity<List<Event>> getAllEvents(){
        now = new DateTime(System.currentTimeMillis());

        try {
            events = googleCalendar.events().list("razviy69@gmail.com")
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

    private void getFreeBus(){
        googleCalendar.freebusy();
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
                    .setApplicationName("Movies")
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

     */
}