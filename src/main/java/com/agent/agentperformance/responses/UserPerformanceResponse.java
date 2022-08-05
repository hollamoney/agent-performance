package com.agent.agentperformance.responses;

import com.agent.agentperformance.entities.User;
import com.agent.agentperformance.entities.UserPerformance;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Data
public class UserPerformanceResponse {

    Long id;

    Long userId;

    String userName;

    Date beginTime;

    Date endTime;

    Date dateInfo;

    int excuseHours;

    String excuseInfo;

    public UserPerformanceResponse(UserPerformance entity){
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.userName =entity.getUser().getUsername();
        this.beginTime = entity.getBeginTime();
        this.endTime = entity.getEndTime();
        this.excuseHours = entity.getExcuseHours();
        this.excuseInfo = entity.getExcuseInfo();
        this.dateInfo = entity.getDateInfo();
    }
}
