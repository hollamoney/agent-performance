package com.agent.agentperformance.responses;

import com.agent.agentperformance.entities.Role;
import com.agent.agentperformance.entities.User;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserResponse {

    Long id;
    String userName;

    String agentId;

    String firstName;

    String lastName;

    Set<Role> roles;





    public UserResponse(User entity) {
        this.id = entity.getId();
        this.userName = entity.getUsername();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.agentId = entity.getAgentId();
        this.roles = entity.getRoles();
    }

}