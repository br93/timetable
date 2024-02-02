package dev.timetable.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import dev.timetable.domain.event.CalendarEventRequest;
import dev.timetable.domain.event.EventResponse;

public interface CalendarEventClient {

    @PostExchange("/{calendarId}/events")
    EventResponse createEvent(@RequestHeader("Authorization") String authorization, @PathVariable String calendarId, @RequestBody CalendarEventRequest request);

    @GetExchange("/{calendarId}/events/{eventId}")
    EventResponse getEvent(@RequestHeader("Authorization") String authorization, @PathVariable String calendarId, @PathVariable String eventId);
    
}
