package com.agent.agentperformance.requests;

import lombok.Data;

import java.util.Date;

@Data
public class PostCreateRequest {

	Long id;

	Date beginTime;

	Date endTime;

	Date dateInfo;

	int excuseHours;

	String excuseInfo;

	Long userId;
}
