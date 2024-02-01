package dev.timetable.domain.event;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventResponse {
    
    private String id;
    private String status;
    private String summary;
    private LocalDateTime created;
    private LocalDateTime updated;
}
