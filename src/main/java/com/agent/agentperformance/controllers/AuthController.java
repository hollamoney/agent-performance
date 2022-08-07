package com.agent.agentperformance.controllers;



import com.agent.agentperformance.entities.Role;
import com.agent.agentperformance.repos.UserRepository;
import com.agent.agentperformance.requests.AuthRequest;
import com.agent.agentperformance.responses.AuthResponse;
import com.agent.agentperformance.services.UserService;
import com.agent.agentperformance.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.agent.agentperformance.entities.User;

import java.util.Collection;
import java.util.Optional;

@RestController
public class AuthController {
    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private UserService userService;


    @Autowired
    JwtTokenUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {


        Optional<User> person = userRepository.findByUserName(request.getUserName());
        String personRoles = person.get().getRole();


        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUserName(),request.getPassword())
            );

            Collection<? extends GrantedAuthority> a  = authentication.getAuthorities();
            boolean authorized = a.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

            System.out.println(authorized);

            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            AuthResponse response = new AuthResponse(user.getUsername(), accessToken,user.getId().toString(),user.getRoles().toString());
            System.out.println(response);
            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


    }

    @PostMapping("/auth/register")
    public ResponseEntity<Void> createUser(@RequestBody User newUser){
        Optional<User> existUser = userRepository.findByUserName(newUser.getUsername());
        if(existUser.isPresent()) {return new ResponseEntity<>(HttpStatus.CONFLICT);}

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(password);

        User user = userService.saveOneUser(newUser);

        User updatedUser = userRepository.save(user);

        if(user != null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}