package wisemen.movienights.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import wisemen.movienights.entities.Media;

import java.util.List;

@Data
public class MediaResults {
    @JsonProperty("Search")
    private List<Media> search;
}
