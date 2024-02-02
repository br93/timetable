package dev.timetable.domain.timetable;

import java.time.Instant;
import java.time.LocalTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Document(collection = "timeslots")
public class Timeslot {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    private LocalTime start;
    private LocalTime end;
    private String createdBy;
    private Instant createdAt;

    public Timeslot(LocalTime start, LocalTime end){
        this.start = start;
        this.end = end;
    }
}
