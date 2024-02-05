package dev.timetable.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import dev.timetable.client.CalendarClient;
import dev.timetable.config.GoogleCalendarConfig.CalendarProperties;
import dev.timetable.domain.calendar.CalendarRequest;
import dev.timetable.domain.calendar.CalendarResponse;

@SpringBootTest
class CalendarServiceTest {
    
    @Autowired
    private CalendarService calendarService;

    @MockBean
    private CalendarClient calendarClient;

    @MockBean
    private CalendarProperties calendarProperties;

    private CalendarResponse mockResponse;
    private CalendarRequest mockRequest;

    private static final String MOCK_ID = "MOCK_ID";
    private static final String MOCK_TOKEN = "MOCK_TOKEN";

    @BeforeEach
    void setup() {
        mockResponse = new CalendarResponse(MOCK_ID, null, null);
        mockRequest = new CalendarRequest();
    }

    @Test
    void getCalendarShouldReturnCalendarResponse(){
        Mockito.when(calendarClient.getCalendar(anyString(), anyString())).thenReturn(mockResponse);
        Assertions.assertEquals(mockResponse, this.calendarService.getCalendar(MOCK_TOKEN, MOCK_ID));
    }

    @Test
    void createCalendarShouldReturnCalendarResponseId(){
        Mockito.when(calendarClient.createCalendar(anyString(), any(CalendarRequest.class))).thenReturn(mockResponse);
        Assertions.assertEquals(MOCK_ID, this.calendarService.createCalendar(MOCK_TOKEN, mockRequest));
    }
}
