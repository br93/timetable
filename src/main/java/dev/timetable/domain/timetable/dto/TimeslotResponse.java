package dev.timetable.domain.timetable.dto;

import java.time.Instant;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TimeslotResponse {

    private LocalTime start;
    private LocalTime end;
    private String createdBy;
    private Instant createdAt;
}
