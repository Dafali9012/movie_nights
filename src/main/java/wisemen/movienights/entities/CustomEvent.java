package wisemen.movienights.entities;

import com.google.api.client.util.DateTime;
import com.nimbusds.oauth2.sdk.util.date.SimpleDate;

import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class CustomEvent {
    private String summary;
    private String location;
    private String description;
    private DateTime start;
    private DateTime end;
    private String[] attendees;

    public CustomEvent(String summary, String location, String description, DateTime start, DateTime end, String[] attendees) {
        this.summary = summary;
        this.location = location;
        this.description = description;
        this.start = start;
        this.end = end;
        this.attendees = attendees;
    }

    public String getSummary() {
        return summary;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public DateTime getStart() {
        return start;
    }

    public DateTime getEnd() {
        return end;
    }

    public String getAttendees() {
        String att ="";
        for (int i = 0; i < attendees.length; i++){
            System.out.println(attendees[i]);
            att +=  attendees[i] + "\t";
        }
        return att;
    }

    @Override
    public String toString() {
        return String.format("summary: %s \n location: %s \n description: %s \n " +
                        "start : %s \n end : %s \n attendees : %s",
                getSummary(), getLocation(),getDescription(), getStart(), getEnd(), getAttendees());
    }
}


class Test {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = sdf.parse("2020/05/11 09:10:44");
        long mill = date.getTime();

        System.out.println(mill);


    }
}