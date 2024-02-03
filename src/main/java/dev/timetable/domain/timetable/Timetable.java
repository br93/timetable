package dev.timetable.domain.timetable;

import java.time.DayOfWeek;
import java.util.List;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@PlanningSolution
public class Timetable {

    private String name;

    @ValueRangeProvider
    private final List<DayOfWeek> days = List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY);

    @PlanningEntityCollectionProperty
    private List<Lesson> lessons;
    
    @ValueRangeProvider
    private List<Timeslot> timeslots;

    @PlanningScore
    private HardSoftScore score;

    public Timetable(String name, List<Lesson> lessons, List<Timeslot> timeslots) {
        this.name = name;
        this.lessons = lessons;
        this.timeslots = timeslots;
    }

}
