package dev.timetable.util;

import java.time.Instant;

import org.springframework.stereotype.Component;

import dev.timetable.domain.timetable.Timeslot;
import dev.timetable.domain.timetable.dto.TimeslotRequest;
import dev.timetable.domain.timetable.dto.TimeslotResponse;

@Component
public class TimeslotMapper {

    public Timeslot toTimeslot(TimeslotRequest request, String author) {
        Timeslot timeslot = new Timeslot(request.getStart(), request.getEnd());
        timeslot.setCreatedAt(Instant.now());
        timeslot.setCreatedBy(author);

        return timeslot;
    }

    public TimeslotResponse toTimeslotResponse(Timeslot timeslot) {
        return new TimeslotResponse(timeslot.getStart(), timeslot.getEnd(), timeslot.getCreatedBy(),
                timeslot.getCreatedAt());
    }
}
