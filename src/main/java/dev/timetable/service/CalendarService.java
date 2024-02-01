package dev.timetable.service;

import org.springframework.stereotype.Service;

import dev.timetable.client.CalendarClient;
import dev.timetable.config.GoogleCalendarConfig.CalendarProperties;
import dev.timetable.domain.calendar.CalendarRequest;
import dev.timetable.domain.calendar.CalendarResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarProperties properties;
    private final CalendarClient calendarClient;

    public CalendarResponse getCalendar(String authorization, String id) {
        return calendarClient.getCalendar(authorization, id);
    }

    public String createCalendar(String authorization, CalendarRequest request) {

        request.setTimeZone(properties.getTimeZone());
        CalendarResponse calendar = calendarClient.createCalendar(authorization, request);
        return calendar.getId();
    }
}
