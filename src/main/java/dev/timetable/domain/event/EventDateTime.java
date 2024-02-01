package dev.timetable.domain.event;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventDateTime {
    
    private LocalDateTime dateTime;
    private String timeZone;
}
