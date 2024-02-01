package dev.timetable.domain.calendar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CalendarRequest {

    private String summary;
    private String description;
    private String timeZone;
   
}
