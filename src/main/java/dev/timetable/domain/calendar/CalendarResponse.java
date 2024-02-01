package dev.timetable.domain.calendar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CalendarResponse {
    
    private String id;
    private String summary;
    private String timeZone;
}
