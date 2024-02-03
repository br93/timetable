package dev.timetable.constraint;

import java.time.Duration;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.Joiners;
import dev.timetable.domain.timetable.Lesson;

public class CustomConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                this.overlappingDayOfWeek(constraintFactory),
                this.difficultSubjectsFarApart(constraintFactory),
                this.easierSubjectsOnFriday(constraintFactory),
                this.overlappingDayOfWeekDifficultSubject(constraintFactory),
                this.overlappingDayOfWeekEasierSubject(constraintFactory),
                this.avoidTwoSubjectsOnFriday(constraintFactory),
                this.overlappingDayOfWeekAndTimeslot(constraintFactory),
                this.preferFullTimeslot(constraintFactory),
                this.sameDayOneHourLesson(constraintFactory)
        };
    }

    private Constraint overlappingDayOfWeek(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(Lesson.class, Joiners.equal(Lesson::getDayOfWeek))
                .penalize(HardSoftScore.ONE_HARD).asConstraint("Day of week conflict");
    }

    private Constraint overlappingDayOfWeekDifficultSubject(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(Lesson.class, Joiners.equal(Lesson::getDayOfWeek))
                .filter((lesson1, lesson2) -> (lesson1.getDayOfWeek() == lesson2.getDayOfWeek())
                        && (lesson1.getLevel() == 3 || lesson2.getLevel() == 3))
                .penalize(HardSoftScore.ONE_HARD).asConstraint("Same day and difficult subject");
    }

    private Constraint overlappingDayOfWeekEasierSubject(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(Lesson.class, Joiners.equal(Lesson::getDayOfWeek))
                .filter((lesson1, lesson2) -> (lesson1.getDayOfWeek() == lesson2.getDayOfWeek())
                        && (lesson1.getLevel() == 1 || lesson2.getLevel() == 1))
                .reward(HardSoftScore.ONE_SOFT).asConstraint("Same day and easier subject");
    }

    private Constraint difficultSubjectsFarApart(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(Lesson.class, Joiners.equal(Lesson::getLevel))
                .filter((lesson1, lesson2) -> (lesson1.getLevel() == lesson2.getLevel()) && lesson1.getLevel() == 3
                        && Math.abs(lesson1.getDayOfWeek().getValue() - lesson2.getDayOfWeek().getValue()) == 1)
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Level of subject conflict");
    }

    private Constraint easierSubjectsOnFriday(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Lesson.class)
                .filter(lesson -> lesson.getDayOfWeek().getValue() == 5 && lesson.getLevel() == 1)
                .reward(HardSoftScore.ONE_SOFT).asConstraint("Easier subject on friday");
    }

    private Constraint avoidTwoSubjectsOnFriday(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(Lesson.class)
                .filter((lesson1, lesson2) -> (lesson1.getDayOfWeek() == lesson2.getDayOfWeek())
                        && lesson1.getDayOfWeek().getValue() == 5)
                .penalize(HardSoftScore.ONE_SOFT).asConstraint("Two subjects on friday");
    }

    private Constraint overlappingDayOfWeekAndTimeslot(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEachUniquePair(Lesson.class, Joiners.equal(Lesson::getDayOfWeek),
                        Joiners.equal(Lesson::getTimeslot))
                .penalize(HardSoftScore.ONE_HARD).asConstraint("Same day and same timeslot conflict");
    }

    private Constraint sameDayOneHourLesson(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(Lesson.class, Joiners.equal(Lesson::getDayOfWeek))
                .filter((lesson1, lesson2) -> (lesson1.getTimeslot().getEnd() == lesson2.getTimeslot().getStart())
                        || lesson2.getTimeslot().getEnd() == lesson1.getTimeslot().getStart())
                .reward(HardSoftScore.ONE_HARD).asConstraint("Same day, different timeslot");
    }

    private Constraint preferFullTimeslot(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Lesson.class).filter(
                lesson -> (Duration.between(lesson.getTimeslot().getStart(), lesson.getTimeslot().getEnd()).toHours() == 2))
                .reward(HardSoftScore.ONE_SOFT).asConstraint("Prefer full hour study");
    }
}
