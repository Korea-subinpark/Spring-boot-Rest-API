package com.example.demo.events;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Event 클래스에 너무 많은 Annotation이 생기는걸 방지
//입력받아야 하는 값들

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class EventDto {
	
	@NotEmpty
	private String name;
	@NotEmpty
	private String description;
	@NotNull
	private LocalDateTime beginEnrollmentDateTime;
	@NotNull
	private	LocalDateTime closeEnrollmentDateTime;
	@NotNull
	private	LocalDateTime beginEventDateTime;
	@NotNull
	private LocalDateTime endEventDateTime;
	private String location; // (optional) 이게 없으면 온라인 모임 
	@Min(0)
	private int basePrice; // (optional)
	@Min(0)
	private int maxPrice; // (optional)
	@Min(0)
	private int	limitOfEnrollment;
	
}
