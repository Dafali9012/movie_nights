package wisemen.movienights.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Media {
    @Id
    private long id;
    @JsonProperty("imdbID")
    private String imdbID;
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Poster")
    private String poster;
    @JsonProperty("Year")
    private int year;
}
