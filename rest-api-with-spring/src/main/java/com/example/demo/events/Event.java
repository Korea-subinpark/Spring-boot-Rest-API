package com.example.demo.events;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.example.demo.accounts.Account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id")
@Entity
public class Event {
	
	@Id @GeneratedValue
	private Integer id;
	
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
	private boolean offline;
	private boolean free;
	
	@ManyToOne
	private Account manager;
	
	@Enumerated(EnumType.STRING)//순서가 바뀌었을 때 혼동을 방지하기 위해 String으로 변경
	private EventStatus eventStatus = EventStatus.DRAFT;

	public void update() {
		//Update free
		if(this.basePrice == 0 && this.maxPrice == 0) {
			this.free = true;
		} else {
			this.free = false;
		}
		
		//Update offline
		if(this.location == null || this.location.isBlank()) {//자바 11버전에 추가된 함수
			this.offline = false;
		} else {
			this.offline = true;
		}
	}
	
	
}
