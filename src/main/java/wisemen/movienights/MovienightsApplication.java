package wisemen.movienights;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovienightsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovienightsApplication.class, args);
	}

}

//TODO google login will return 2 tokens
// 1 is the validation token that expires after some minutes
// and the oder one is a refresh toke that will send to google
// to get a new validation token
