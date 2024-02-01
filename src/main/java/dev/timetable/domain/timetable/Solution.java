package dev.timetable.domain.timetable;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
//@Document("solutions")
public class Solution {

    /*@MongoId(FieldType.OBJECT_ID)
    private String id;*/
    
    private List<Lesson> lessons;
    private String createdBy;
    
}
