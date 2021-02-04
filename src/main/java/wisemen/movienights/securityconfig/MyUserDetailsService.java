package wisemen.movienights.securityconfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import wisemen.movienights.entities.User;
import wisemen.movienights.repositories.UserRepository;

@Configuration
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public BCryptPasswordEncoder getEncoder() { return encoder; }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found by email: " + email);
        }
        return toUserDetails(user);
    }

    public User addUser(User user){
        User newUser = new User(user.getName(), user.getEmail(), encoder.encode(user.getPassword()));
        try{
            userRepository.save(newUser);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private UserDetails toUserDetails(User user) {

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("USER").build();
    }
}
