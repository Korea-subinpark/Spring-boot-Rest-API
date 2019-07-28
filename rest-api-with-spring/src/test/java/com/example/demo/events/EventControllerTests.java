package com.example.demo.events;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.common.RestDocsConfiguration;
import com.example.demo.common.TestDescription;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
public class EventControllerTests {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	@TestDescription("정상적으로 이벤트를 생성하는 테스트")
	public void createEvent() throws Exception {
		EventDto event = EventDto.builder()
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
		
		mockMvc.perform(post("/api/events/")
					.contentType(MediaType.APPLICATION_JSON_UTF8)//요청 타입
					.accept(MediaTypes.HAL_JSON)//어떤 응답을 원하는지
					.content(objectMapper.writeValueAsString(event))
				)
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(header().exists(HttpHeaders.LOCATION))
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
				//.andExpect(jsonPath("free").value(Matchers.not(true)))
				.andExpect(jsonPath("_links.self").exists())
				.andExpect(jsonPath("_links.query-events").exists())
				.andExpect(jsonPath("_links.update-event").exists())
				.andDo(document("create-event",
						links(
								linkWithRel("self").description("link to self"),
								linkWithRel("query-events").description("link to query events"),
								linkWithRel("update-event").description("link to update an existing")
						),
						requestHeaders(
								headerWithName(HttpHeaders.ACCEPT).description("accept header"),
								headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
						),
						requestFields(
								fieldWithPath("name").description("Name of new event"),
								fieldWithPath("description").description("description of new event"),
								fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
								fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
								fieldWithPath("beginEventDateTime").description("date time of begin of new event"),
								fieldWithPath("endEventDateTime").description("date time of end of new event"),
								fieldWithPath("location").description("location of new event"),
								fieldWithPath("basePrice").description("base price of new event"),
								fieldWithPath("maxPrice").description("max price of new event"),
								fieldWithPath("limitOfEnrollment").description("limit of enrollment")
						),
						responseHeaders(
								headerWithName(HttpHeaders.LOCATION).description("location header"),
								headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
						),
						relaxedResponseFields(//relaxed 장점:문서 일부분만 테스트 가능 / 단점:정확한 문서를 생성하지 못한다 
								fieldWithPath("id").description("identifier of new event"),
								fieldWithPath("name").description("Name of new event"),
								fieldWithPath("description").description("description of new event"),
								fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
								fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
								fieldWithPath("beginEventDateTime").description("date time of begin of new event"),
								fieldWithPath("endEventDateTime").description("date time of end of new event"),
								fieldWithPath("location").description("location of new event"),
								fieldWithPath("basePrice").description("base price of new event"),
								fieldWithPath("maxPrice").description("max price of new event"),
								fieldWithPath("limitOfEnrollment").description("limit of enrollment"),
								fieldWithPath("free").description("it tells if this event is free or not"),
								fieldWithPath("offline").description("it tells if this event is offline of online"),
								fieldWithPath("eventStatus").description("event status")
						)
				));
	}
	
	
	@Test
	@TestDescription("입력받을 수 없는 값이 입력되었을 때 테스트")
	public void createEventBadRequest() throws Exception {
		Event event = Event.builder()
				.id(100)
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
				.free(true)
				.offline(false)
				.eventStatus(EventStatus.PUBLISHED)
				.build();
		
		mockMvc.perform(post("/api/events/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)//요청 타입
				.accept(MediaTypes.HAL_JSON)//어떤 응답을 원하는지
				.content(objectMapper.writeValueAsString(event)))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@TestDescription("입력값이 비어있을 때 테스트")
	public void createEventBadRequestEmptyInput() throws Exception {
		EventDto eventDto = EventDto.builder().build();
		
		this.mockMvc.perform(post("/api/events")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(this.objectMapper.writeValueAsString(eventDto)))
				.andExpect(status().isBadRequest());
		
	}
	
	@Test
	@TestDescription("입력값이 잘못된 경우 테스트")
	public void createEventBadRequestWrongInput() throws Exception {
		EventDto eventDto = EventDto.builder()
				.name("Spring")
				.description("REST API Development with Spring")
				.beginEnrollmentDateTime(LocalDateTime.of(2019, 07, 26, 23, 44))//끝나는 날이 더 빠른 경우
				.closeEnrollmentDateTime(LocalDateTime.of(2019, 07, 25, 23, 44))
				.beginEventDateTime(LocalDateTime.of(2019, 07, 24, 23, 44))
				.endEventDateTime(LocalDateTime.of(2019, 07, 23, 23, 44))
				.basePrice(10000)
				.maxPrice(200)
				.limitOfEnrollment(100)
				.location("Seoul")
				.build();
		
		this.mockMvc.perform(post("/api/events")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(this.objectMapper.writeValueAsString(eventDto)))
				.andExpect(status().isBadRequest())
//				.andExpect(jsonPath("$[0].objectName").exists())
//				.andExpect(jsonPath("$[0].defaultMessage").exists())
//				.andExpect(jsonPath("$[0].code").exists())
			;
		
	}
	
}
