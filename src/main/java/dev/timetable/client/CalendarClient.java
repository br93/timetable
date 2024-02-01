package dev.timetable.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import dev.timetable.domain.calendar.CalendarRequest;
import dev.timetable.domain.calendar.CalendarResponse;

public interface CalendarClient {

    @PostExchange
    CalendarResponse createCalendar(@RequestHeader("Authorization") String authorization, @RequestBody CalendarRequest request);

    @GetExchange("/{id}")
    CalendarResponse getCalendar(@RequestHeader("Authorization") String authorization, @PathVariable String id);
    
}
