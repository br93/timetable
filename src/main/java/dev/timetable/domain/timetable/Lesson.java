package dev.timetable.domain.timetable;

import java.time.DayOfWeek;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import lombok.Data;
import lombok.NoArgsConstructor;

@PlanningEntity
@NoArgsConstructor
@Data
public class Lesson {

    @PlanningId
    private Long lessonId;

    /*@MongoId(FieldType.OBJECT_ID)
    private String id;*/

    private String subject;

    @PlanningVariable
    private DayOfWeek dayOfWeek;

    private Timeslot timeslot;

    private int level;

    public Lesson(Long lessonId, String subject, Timeslot timeslot, int level){
        this.lessonId = lessonId;
        this.subject = subject;
        this.timeslot = timeslot;
        this.level = level;
    }
    
}
