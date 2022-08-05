package com.agent.agentperformance.controllers;

import com.agent.agentperformance.entities.Role;
import com.agent.agentperformance.entities.User;
import com.agent.agentperformance.exceptions.UserNotFoundException;
import com.agent.agentperformance.repos.UserRepository;
import com.agent.agentperformance.responses.UserResponse;
import com.agent.agentperformance.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    private UserRepository userRepository;


    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public  List<UserResponse> getAllUsers() {

        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody User newUser){
        Optional<User> existUser = userRepository.findByUserName(newUser.getUsername());
        if(existUser.isPresent()) {return new ResponseEntity<>(HttpStatus.CONFLICT);}

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(password);

        User user = userService.saveOneUser(newUser);

        user.addRole(new Role(Long.valueOf(4)));

        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        User updatedUser = userRepository.save(user);

        if(user != null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{userId}")
    public UserResponse getOneUser(@PathVariable Long userId){
        User user = userService.getOneUserById(userId);
        if(user == null) {
            throw new UserNotFoundException();
        }
        return new UserResponse(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateOneUser(@PathVariable Long userId, @RequestBody User newUser) {
        User user = userService.updateOneUser(userId, newUser);
        if(user != null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @DeleteMapping("/{userId}")
    public void deleteOneUser(@PathVariable Long userId) {
        userService.deleteById(userId);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleUserNotFound() {

    }

}
