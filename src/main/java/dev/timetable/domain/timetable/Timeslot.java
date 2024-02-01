package dev.timetable.domain.timetable;

import java.time.LocalTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
//@Document(collection = "timeslots")
public class Timeslot {

    /*@MongoId(FieldType.OBJECT_ID)
    private String id;*/

    private LocalTime start;
    private LocalTime end;
    private String createdBy;

    public Timeslot(LocalTime start, LocalTime end){
        this.start = start;
        this.end = end;
    }
}
