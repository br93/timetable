package dev.timetable.controller;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.timetable.domain.calendar.CalendarRequest;
import dev.timetable.domain.calendar.CalendarResponse;
import dev.timetable.domain.event.EventRequest;
import dev.timetable.domain.event.EventResponse;
import dev.timetable.service.CalendarService;
import dev.timetable.service.EventService;
import dev.timetable.service.TokenService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("calendars")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;
    private final TokenService tokenService;
    private final EventService eventService;

    @GetMapping
    public String createCalendar(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient, @RequestBody CalendarRequest request){
        String authorization = tokenService.getToken(authorizedClient);
        return calendarService.createCalendar(authorization, request);
    }

    @GetMapping("{id}")
    public CalendarResponse getCalendar(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient, @PathVariable String id){
        String authorization = tokenService.getToken(authorizedClient);
        return calendarService.getCalendar(authorization, id);
    }

    @GetMapping("{calendarId}/events")
    public String createEvent(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient, @PathVariable String calendarId, @RequestBody EventRequest request){
        String authorization = tokenService.getToken(authorizedClient);
        return eventService.createEvent(authorization, calendarId, request);
    }

    @GetMapping("{calendarId}/events/{eventId}")
    public EventResponse getEvent(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient, @PathVariable String calendarId, @PathVariable String eventId){
        String authorization = tokenService.getToken(authorizedClient);
        return eventService.getEvent(authorization, calendarId, eventId);
    }
    
}
