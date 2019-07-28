package com.example.demo.events;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Event 클래스에 너무 많은 Annotation이 생기는걸 방지
//입력받아야 하는 값들

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class EventDto {

	private String name;
	private String description;
	private LocalDateTime beginEnrollmentDateTime;
	private	LocalDateTime closeEnrollmentDateTime;
	private	LocalDateTime beginEventDateTime;
	private LocalDateTime endEventDateTime;
	private String location; // (optional) 이게 없으면 온라인 모임 
	private int basePrice; // (optional)
	private int maxPrice; // (optional)
	private int	limitOfEnrollment;
	
}
