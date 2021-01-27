package wisemen.movienights.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wisemen.movienights.entities.User;
import wisemen.movienights.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity addNewUser(@RequestBody User user){
        return userService.addNewUser(user);
    }

    @GetMapping("/all")
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{email}")
    public User getPersonByName(@PathVariable String email){

        System.out.println("Email "+ email);
        System.out.println("findByEmail"+ userService.findUserByEmail(email));
        return userService.findUserByEmail(email);
    }
}
