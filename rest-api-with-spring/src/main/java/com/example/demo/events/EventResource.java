package com.example.demo.events;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class EventResource extends Resource<Event> {

	public EventResource(Event event, Link... links) {
		super(event, links);
		add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
	}
	
}

//public class EventResource extends ResourceSupport {
//	
//	@JsonUnwrapped //json에서 event 껍데기를 없애고 싶을 때
//	private Event event;
//	
//	public EventResource(Event event) {
//		this.event = event;
//	}
//	
//	public Event getEvent() {
//		return event;
//	}
//}
