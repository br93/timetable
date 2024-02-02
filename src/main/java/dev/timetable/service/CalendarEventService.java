package dev.timetable.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.timetable.client.CalendarEventClient;
import dev.timetable.config.GoogleCalendarConfig.CalendarProperties;
import dev.timetable.domain.event.EventReminder;
import dev.timetable.domain.event.CalendarEventRequest;
import dev.timetable.domain.event.EventResponse;
import dev.timetable.domain.event.ItemReminder;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarEventService {
    
    private final CalendarEventClient eventClient;
    private final CalendarProperties properties;

    public EventResponse getEvent(String authorization, String calendarId, String eventId){
        return eventClient.getEvent(authorization, calendarId, eventId);
    }

    public String createEvent(String authorization, String calendarId, CalendarEventRequest request){
        
        request.setRecurrence(List.of(properties.getRecurrence()));
        request.getEnd().setTimeZone(properties.getTimeZone());
        request.getStart().setTimeZone(properties.getTimeZone());

        String[] reminders = properties.getReminder().split(",");
        ItemReminder email = new ItemReminder(reminders[0], 24*60);
        ItemReminder popup = new ItemReminder(reminders[1], 10);
        EventReminder eventReminder = new EventReminder(List.of(email, popup), false);
        request.setReminders(eventReminder);
      
        var event = eventClient.createEvent(authorization, calendarId, request);
        return event.getId();
    }
}

    
