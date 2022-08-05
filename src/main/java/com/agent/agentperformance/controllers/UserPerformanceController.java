package com.agent.agentperformance.controllers;

import com.agent.agentperformance.entities.User;
import com.agent.agentperformance.entities.UserPerformance;
import com.agent.agentperformance.exceptions.UserNotFoundException;
import com.agent.agentperformance.repos.UserRepository;
import com.agent.agentperformance.requests.PostCreateRequest;
import com.agent.agentperformance.responses.UserPerformanceResponse;
import com.agent.agentperformance.services.UserPerformanceService;
import com.agent.agentperformance.services.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/performance")
public class UserPerformanceController {

    private UserPerformanceService userPerformanceService;


    public UserPerformanceController(UserPerformanceService userPerformanceService, UserService userService) {
        this.userPerformanceService = userPerformanceService;

    }
    @GetMapping
    public List<UserPerformanceResponse> getAllPerformance(@RequestParam Optional<Long> userId) {
        return userPerformanceService.getAllPerformances(userId);
    }

    @PostMapping
    public UserPerformance createDailyPerformance(@RequestBody PostCreateRequest postCreateRequest) {

        return userPerformanceService.saveOneUserPerformance(postCreateRequest);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateOneUser(@PathVariable Long userId, @RequestBody UserPerformance newUser) {
        UserPerformance user = userPerformanceService.updateOneUserPerformance(userId, newUser);
        if(user != null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @DeleteMapping("/{performanceId}")
    public void deleteOnePost(@PathVariable Long performanceId) {
        userPerformanceService.deleteOneUserPerformanceById(performanceId);
    }

    @GetMapping("/{userId}")
    public List<UserPerformanceResponse> getOneUserPerformance(@PathVariable Long userId) {
        List<UserPerformance> userPerformance = userPerformanceService.getOneUserPerformanceByUserId(userId);
        if(userPerformance == null){
            throw new UserNotFoundException();
        }
        return userPerformance.stream().map(user -> new UserPerformanceResponse(user)).collect(Collectors.toList());
    }

    @GetMapping("/date")
    public List<UserPerformanceResponse> getBetweenDatePerformance(@RequestParam Optional<String> first,
                                                                   @RequestParam Optional<String> second,
                                                                   @RequestParam Optional<Long> userId) throws ParseException {
        //SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        //Date first1 = formatter.parse(String.valueOf(first));
        //Date second1 = formatter.parse(String.valueOf(second));
        return userPerformanceService.getBetweenDatewithUserId(first, second, userId);
    }

}
