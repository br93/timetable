package dev.timetable.domain.calendar;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document("calendar-logs")
public class CalendarLog {

    @MongoId(FieldType.OBJECT_ID)
    private String id;

    private String calendarId;
    private List<String> eventId;
    private String createdBy;
    
}
