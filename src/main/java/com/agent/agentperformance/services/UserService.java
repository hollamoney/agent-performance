package com.agent.agentperformance.services;

import com.agent.agentperformance.entities.User;
import com.agent.agentperformance.repos.UserRepository;
import com.agent.agentperformance.responses.UserResponse;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUsers() {
        List<User> users;

        users = userRepository.findAll();

        return users.stream().map(user -> new UserResponse(user)).collect(Collectors.toList());

    }

    public User saveOneUser(User newUser) { return userRepository.save(newUser);}

    public User getOneUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateOneUser(Long userId, User newUser) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            User foundUser = user.get();
            foundUser.setAgentId(newUser.getAgentId());
            System.out.println(newUser.getUsername());
            foundUser.setUserName(newUser.getUsername());
            foundUser.setFirstName(newUser.getFirstName());
            foundUser.setLastName(newUser.getLastName());
            System.out.println(foundUser.getUsername());
            userRepository.save(foundUser);
            return foundUser;
        }else
            return null;
    }

    public void deleteById(Long userId){
        try {
            userRepository.deleteById(userId);
        }catch(EmptyResultDataAccessException e){
            System.out.println("User " + userId + " doesn't exist");
        }
    }

    public User getOneUserByAgentId(String agentId) {
        return userRepository.findByAgentId(agentId);
    }

    public User getOneUserByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(null);
    }
}
