package com.agent.agentperformance.services;

import com.agent.agentperformance.entities.User;
import com.agent.agentperformance.entities.UserPerformance;
import com.agent.agentperformance.repos.UserPerformanceRepository;
import com.agent.agentperformance.requests.PostCreateRequest;
import com.agent.agentperformance.responses.UserPerformanceResponse;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserPerformanceService {

    private UserPerformanceRepository userPerformanceRepository;

    private UserService userService;

    public UserPerformanceService(UserPerformanceRepository userPerformanceRepository, UserService userService) {
        this.userPerformanceRepository = userPerformanceRepository;
        this.userService = userService;
    }
    public List<UserPerformanceResponse> getAllPerformances(Optional<Long> userId) {
        List<UserPerformance> lists;
        if(userId.isPresent()) {
            lists = userPerformanceRepository.findByUserId(userId.get());
        }else
            lists = userPerformanceRepository.findAll();
        return lists.stream().map(list -> new UserPerformanceResponse(list)).collect(Collectors.toList());
    }

    public UserPerformance saveOneUserPerformance(PostCreateRequest postCreateRequest) {
        User user = userService.getOneUserById(postCreateRequest.getUserId());
        if (user == null)
            return null;
        UserPerformance toSave = new UserPerformance();
        toSave.setExcuseInfo(postCreateRequest.getExcuseInfo());
        toSave.setBeginTime(postCreateRequest.getBeginTime());
        toSave.setEndTime(postCreateRequest.getEndTime());
        toSave.setDateInfo(postCreateRequest.getDateInfo());
        toSave.setExcuseHours(postCreateRequest.getExcuseHours());
        toSave.setUser(user);
        System.out.println(postCreateRequest.getDateInfo());

        return userPerformanceRepository.save(toSave);
    }
    public List<UserPerformanceResponse> getBetweenDatewithUserId(Optional<String> first,
                                                                  Optional<String> second,
                                                                  Optional<Long> userId){
        List<UserPerformance> lists;
        if(first.isPresent() && second.isPresent() && userId.isPresent()) {
            lists = userPerformanceRepository.findBetweenTwoDateswithUserId(first,second,userId);
        } else if (first.isPresent() && second.isPresent() ) {
            lists = userPerformanceRepository.findBetweenTwoDates(first,second);
        } else if (userId.isPresent()){
            lists = userPerformanceRepository.findByUserId((Long) userId.orElse(2L));
        }else
            lists = userPerformanceRepository.findAll();
        return lists.stream().map(list -> new UserPerformanceResponse(list)).collect(Collectors.toList());

    }

    public UserPerformance updateOneUserPerformance(Long userId, UserPerformance newUser) {
        Optional<UserPerformance> user = userPerformanceRepository.findById(userId);
        if(user.isPresent()){
            UserPerformance foundUser = user.get();
            foundUser.setBeginTime(newUser.getBeginTime());
            foundUser.setEndTime(newUser.getEndTime());
            foundUser.setExcuseHours(newUser.getExcuseHours());
            foundUser.setExcuseInfo(newUser.getExcuseInfo());
            System.out.println(newUser.getDateInfo());
            foundUser.setDateInfo(newUser.getDateInfo());
            userPerformanceRepository.save(foundUser);
            return foundUser;
        }else
            return null;
    }

    public List<UserPerformance> getOneUserPerformanceByUserId(Long userId) {
        return userPerformanceRepository.findByUserId(userId);
    }

    public void deleteOneUserPerformanceById(Long performanceId) {
        userPerformanceRepository.deleteById(performanceId);
    }
}
