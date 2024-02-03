package dev.timetable.domain.timetable.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LessonRequest {
    
    @NotBlank(message = "Subject must not be blank")
    private String subject;

    @Min(value = 1, message = "Minimum level = 1")
    @Max(value = 3, message = "Maximum level = 3")
    private int level;
}
