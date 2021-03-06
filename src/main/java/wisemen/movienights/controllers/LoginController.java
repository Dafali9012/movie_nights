package wisemen.movienights.controllers;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import wisemen.movienights.services.UserService;

import java.io.IOException;

@SuppressWarnings("deprecation")
@RestController
public class LoginController {
    //@Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String CLIENT_ID = "834224170973-rafg4gcu10p2dbjk594ntg8696ucq06q.apps.googleusercontent.com";
    //@Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String CLIENT_SECRET = "8qMXbutui-w-ygkf7UfBIjw0";

    @Autowired
    private UserService userService;

    @PostMapping("/api/storeauthcode")
    public String storeauthcode(@RequestBody String code, @RequestHeader("X-Requested-With") String encoding) {

        if (encoding == null || encoding.isEmpty()) {
            return "Error, wrong headers";
        }

        GoogleTokenResponse tokenResponse = null;
        try {
            tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    "https://www.googleapis.com/oauth2/v4/token",
                    CLIENT_ID,
                    CLIENT_SECRET,
                    code,
                    "http://localhost:5500")
                    .execute();

            userService.createUser(
                tokenResponse.getAccessToken(),
                tokenResponse.getRefreshToken(),
                System.currentTimeMillis() + tokenResponse.getExpiresInSeconds() * 1000,
                tokenResponse.parseIdToken().getPayload().getEmail(),
                (String) tokenResponse.parseIdToken().getPayload().get("name")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        // Store these 3 in DB
        String accessToken = tokenResponse.getAccessToken();
        String refreshToken = tokenResponse.getRefreshToken();
        Long expiresAt = System.currentTimeMillis() + (tokenResponse.getExpiresInSeconds() * 1000);

        // Debug purpose only
        System.out.println("accessToken: " + accessToken);
        System.out.println("refreshToken: " + refreshToken);
        System.out.println("expiresAt: " + expiresAt);

        // Get profile info from ID token (Obtained at the last step of OAuth2)
        GoogleIdToken idToken = null;
        try {
            idToken = tokenResponse.parseIdToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GoogleIdToken.Payload payload = idToken.getPayload();

        // Use THIS ID as a key to identify a google user-account.
        String userId = payload.getSubject();

        String email = payload.getEmail();
        boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");
        String locale = (String) payload.get("locale");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");

        // Debugging purposes, should probably be stored in the database instead (At least "givenName").
        System.out.println("userId: " + userId);
        System.out.println("email: " + email);
        System.out.println("emailVerified: " + emailVerified);
        System.out.println("name: " + name);
        System.out.println("pictureUrl: " + pictureUrl);
        System.out.println("locale: " + locale);
        System.out.println("familyName: " + familyName);
        System.out.println("givenName: " + givenName);


         */
        return "OK";
    }
}
