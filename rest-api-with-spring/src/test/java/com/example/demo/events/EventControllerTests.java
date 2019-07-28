package com.example.demo.events;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@WebMvcTest
public class EventControllerTests {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean//WebMvcTest는 Web 관련된 것만 의존성을 주입한다
	EventRepository eventRepository;
	
	@Test
	public void createEvent() throws Exception {
		Event event = Event.builder()
				.name("Spring")
				.description("REST API Development with Spring")
				.beginEnrollmentDateTime(LocalDateTime.of(2019, 07, 27, 23, 44))
				.closeEnrollmentDateTime(LocalDateTime.of(2019, 07, 27, 23, 44))
				.beginEventDateTime(LocalDateTime.of(2019, 07, 27, 23, 44))
				.endEventDateTime(LocalDateTime.of(2019, 07, 27, 23, 44))
				.basePrice(100)
				.maxPrice(200)
				.limitOfEnrollment(100)
				.location("Seoul")
				.build();
		event.setId(10);
		Mockito.when(eventRepository.save(event)).thenReturn(event);
		
		mockMvc.perform(post("/api/events/")
					.contentType(MediaType.APPLICATION_JSON_UTF8)//요청 타입
					.accept(MediaTypes.HAL_JSON)//어떤 응답을 원하는지
					.content(objectMapper.writeValueAsString(event))
				)
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("id").exists())
		;
	}
	
}
