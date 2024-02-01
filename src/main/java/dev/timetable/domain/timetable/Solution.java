package dev.timetable.domain.timetable;

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
@Document(collection = "solutions")
public class Solution {

    @MongoId(FieldType.OBJECT_ID)
    private String id;
    
    private List<Lesson> lessons;
    private String createdBy;    
}
