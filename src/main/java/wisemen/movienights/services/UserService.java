package wisemen.movienights.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wisemen.movienights.controllers.LoginController;
import wisemen.movienights.entities.User;
import wisemen.movienights.repositories.UserRepository;

import java.util.Optional;

@SuppressWarnings("deprecation")
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoogleCalendarEventsService googleCalendarEventsService;

    public void createUser(String accessToken, String refreshToken, Long expiresAt, String email) {
        User newuser = new User(email, accessToken, refreshToken, expiresAt);
        Optional<User> databaseUser = userRepository.findById(email);
        if(databaseUser.isPresent()) {
            if(System.currentTimeMillis() > expiresAt) {
                GoogleCredential refreshedCredentials = googleCalendarEventsService.getRefreshedCredentials(refreshToken);
                newuser.setAccessToken(refreshedCredentials.getAccessToken());
                newuser.setRefreshToken(refreshedCredentials.getRefreshToken());
                newuser.setExpiresAt(refreshedCredentials.getExpiresInSeconds()*1000);
                userRepository.save(newuser);
            }
        } else {
            userRepository.save(newuser);
        }
    }

    public User getUser(String email) {
        return userRepository.findById(email).get();
    }
}
