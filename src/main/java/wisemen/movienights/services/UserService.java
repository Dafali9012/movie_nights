package wisemen.movienights.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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



    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User findUserByEmail(String email){
        return  userRepository.findByEmail(email);
    }

    public ResponseEntity<User> addNewUser(User user){
        boolean isValidEmail =  isEmailTaken(user.getEmail());

        if (!isValidEmail){
            Error error = new Error("Email already in use;");
            return new ResponseEntity(error.getMessage(), HttpStatus.CONFLICT);
        }

        else {
            detailsService.addUser(user);
            return new ResponseEntity(HttpStatus.CREATED);
        }

    }

    private boolean isEmailTaken(String email){
        User user = userRepository.findByEmail(email);
        return user == null;

    }
}
