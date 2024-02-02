package dev.timetable.domain.timetable.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LessonRequest {
    
    private String subject;
    private int level;
}
