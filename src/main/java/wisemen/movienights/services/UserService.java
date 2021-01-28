package wisemen.movienights.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import wisemen.movienights.entities.User;
import wisemen.movienights.repositories.UserRepository;
import wisemen.movienights.securityconfig.MyUserDetailsService;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MyUserDetailsService detailsService;


    public ResponseEntity<User> findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            User newUser = new User(user.getId(), user.getName(), user.getEmail());
            return new ResponseEntity<User>(newUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<User> addNewUser(User user) {
        boolean isValidEmail = isEmailTaken(user.getEmail());
        if (!isValidEmail) {
            Error error = new Error("Email already in use;");
            return new ResponseEntity(error.getMessage(), HttpStatus.CONFLICT);
        } else {
            detailsService.addUser(user);
            return new ResponseEntity(HttpStatus.CREATED);
        }
    }

    public ResponseEntity<User> update(Authentication authentication, int id, String name, String email) {
        User user = userRepository.findById(id);

        switch (validateAuthentication(authentication,id)){
            case UNAUTHORIZED:
                return new ResponseEntity("Unauthorized",HttpStatus.UNAUTHORIZED);
            case OK:
                if (name != null) user.setName(name);
                if (email != null) user.setEmail(email);
                userRepository.save(user);
                return findById(id);
            case FORBIDDEN:
                return new ResponseEntity("Not Allowed",HttpStatus.FORBIDDEN);
            default:
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    private boolean isEmailTaken(String email) {
        User user = userRepository.findByEmail(email);
        return user == null;
    }

    public ResponseEntity<User> findById(int id) {
        User user = userRepository.findById(id);
        if (user != null) {
            User newUser = new User(user.getId(), user.getName(), user.getEmail());
            return new ResponseEntity<User>(newUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    public HttpStatus validateAuthentication(Authentication authentication, int id) {
        if (authentication == null) return HttpStatus.UNAUTHORIZED;

        User user = userRepository.findById(id);
        boolean isAuthenticated = authentication.isAuthenticated();

        if (isAuthenticated && (user.getEmail().equals(authentication.getName()))) return HttpStatus.OK;
        if (isAuthenticated && !user.getEmail().equals(authentication.getName())) return HttpStatus.FORBIDDEN;
        return HttpStatus.UNAUTHORIZED;
    }

    public HttpStatus validateAuthentication(Authentication authentication, String email) {
        if (authentication == null) return HttpStatus.UNAUTHORIZED;

        User user = userRepository.findByEmail(email);
        boolean isAuthenticated = authentication.isAuthenticated();

        if (isAuthenticated && (user.getEmail().equals(authentication.getName()))) return HttpStatus.OK;
        if (isAuthenticated && !user.getEmail().equals(authentication.getName())) return HttpStatus.FORBIDDEN;
        return HttpStatus.UNAUTHORIZED;
    }
}