package dev.timetable.domain.timetable.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TimeslotRequest {

    private LocalTime start;
    private LocalTime end;

}
