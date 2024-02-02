package dev.timetable.domain.event;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CalendarEventRequest {
    
    private EventDateTime start;
    private EventDateTime end;
    private List<String> recurrence;
    private String summary;
    private EventReminder reminders;
}
