package wisemen.movienights.entities;


import java.util.List;

public class CustomEvent {
    private String summary;
    private String location;
    private String description;
    private String start;
    private String end;
    private List<String> attendees;

    public CustomEvent(String summary, String location, String description, String start, String end, List<String> attendees) {
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

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public List<String> getAttendees() {
        return attendees;
    }

    @Override
    public String toString() {
        return String.format("summary: %s \n location: %s \n description: %s \n " +
                        "start : %s \n end : %s \n attendees : %s",
                getSummary(), getLocation(),getDescription(), getStart(), getEnd(), getAttendees());
    }
}