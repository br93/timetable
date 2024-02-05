package dev.timetable.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import dev.timetable.client.CalendarEventClient;
import dev.timetable.config.GoogleCalendarConfig.CalendarProperties;
import dev.timetable.domain.event.CalendarEventRequest;
import dev.timetable.domain.event.EventDateTime;
import dev.timetable.domain.event.EventResponse;

@SpringBootTest
class CalendarEventServiceTest {
    
    @Autowired
    private CalendarEventService calendarEventService;

    @MockBean
    private CalendarEventClient calendarEventClient;

    @MockBean
    private CalendarProperties calendarProperties;

    private EventResponse mockResponse;

    private CalendarEventRequest mockRequest;

    private static final String MOCK_TOKEN = "MOCK-TOKEN";
    private static final String MOCK_ID = "MOCK-ID";
    private static final String MOCK_STATUS = "MOCK-STATUS";
    private static final String MOCK_SUMMARY = "MOCK-SUMMARY";
    private static final LocalDateTime MOCK_CREATED = LocalDateTime.now();
    private static final LocalDateTime MOCK_UPDATED = LocalDateTime.now();

    @BeforeEach
    void setup() {
        mockResponse = new EventResponse(MOCK_ID, MOCK_STATUS, MOCK_SUMMARY, MOCK_CREATED, MOCK_UPDATED);
        mockRequest = new CalendarEventRequest(new EventDateTime(MOCK_CREATED, null), new EventDateTime(MOCK_UPDATED, null), null, MOCK_SUMMARY, null);
    }

    @Test
    void getEventShouldReturnEventResponse(){
        Mockito.when(calendarEventClient.getEvent(anyString(), anyString(), anyString())).thenReturn(mockResponse);
        Assertions.assertEquals(mockResponse, this.calendarEventService.getEvent(MOCK_TOKEN, MOCK_ID, MOCK_ID));
    }

    @Test
    void createEventShouldReturnEventResponseId(){
        Mockito.when(calendarEventClient.createEvent(anyString(), anyString(), any(CalendarEventRequest.class))).thenReturn(mockResponse);
        Mockito.when(calendarProperties.getRecurrence()).thenReturn("MOCK-RECURRENCE");
        Mockito.when(calendarProperties.getReminder()).thenReturn("test,test");
        Assertions.assertEquals(mockResponse.getId(), this.calendarEventService.createEvent(MOCK_TOKEN, MOCK_ID, mockRequest));
    }
}
