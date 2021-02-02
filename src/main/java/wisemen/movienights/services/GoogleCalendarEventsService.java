package wisemen.movienights.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class GoogleCalendarEventsService {
    private List<Event> calendarEvents;

    public ResponseEntity initializeGoogleCalendar(String accessToken){
        Events events = null;
        Calendar calendar = null;
        DateTime now = new DateTime(System.currentTimeMillis());
        GoogleCredential credentials = new GoogleCredential().setAccessToken(accessToken);

        try {
          calendar = new Calendar.Builder(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    credentials)
                    .setApplicationName("Movie Nights")
                    .build();
        } catch (Exception e){
            System.out.println("Exception ");
            e.printStackTrace();
        }

        try {
            events = calendar.events().list("primary")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        calendarEvents = events.getItems();


        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    public List<Event> getAllEvents(){
        return calendarEvents;
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
        return responseCode == 200;
    }

}

