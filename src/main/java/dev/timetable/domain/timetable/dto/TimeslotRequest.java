package dev.timetable.domain.timetable.dto;

import java.time.LocalTime;

import dev.timetable.validation.TimeslotValid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TimeslotValid(message = "Invalid timeslot: start time must be before end time and the duration must be 2 hours.")
public class TimeslotRequest {

    @NotNull(message = "start must not be null")
    private LocalTime start;

    @NotNull(message = "end must not be null")
    private LocalTime end;

}
