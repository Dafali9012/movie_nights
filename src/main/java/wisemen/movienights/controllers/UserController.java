package wisemen.movienights.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    @ResponseBody
    public ResponseEntity addNewUser(@RequestBody User user){
        return userService.addNewUser(user);
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByName(@PathVariable String email){
        return userService.findUserByEmail(email);
    }

    @GetMapping("/id")
    public ResponseEntity<User> getUserById(@PathVariable int id){
        return userService.findById(id);
    }


    @RequestMapping(value = "/whoami", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> currentUserName(Authentication authentication) {
        if (authentication.getName() != null){
              return userService.findUserByEmail(authentication.getName());
        }
        else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(path = "{personId}")
    public ResponseEntity<User> updatePerson(
            Authentication authentication,
            @PathVariable("personId") int id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email){

        return userService.update(authentication,id,name, email);
    }
}
