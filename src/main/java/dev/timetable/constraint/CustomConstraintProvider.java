package dev.timetable.constraint;

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
                this.easierSubjectsOnFriday(constraintFactory)
        };
    }

    private Constraint overlappingDayOfWeek(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(Lesson.class, Joiners.equal(Lesson::getDayOfWeek))
                .penalize(HardSoftScore.ONE_HARD).asConstraint("Day of week conflict");
    }

    private Constraint difficultSubjectsFarApart(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(Lesson.class, Joiners.equal(Lesson::getLevel))
                .filter((lesson1, lesson2) -> (lesson1.getLevel() == lesson2.getLevel()) && lesson1.getLevel() == 3
                        && lesson1.getDayOfWeek().getValue() - lesson2.getDayOfWeek().getValue() == -1)
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Level of subject conflict");
    }

    private Constraint easierSubjectsOnFriday(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Lesson.class)
                .filter(lesson -> lesson.getDayOfWeek().getValue() == 5 && lesson.getLevel() == 1)
                .reward(HardSoftScore.ONE_SOFT).asConstraint("Easier subject on friday");
    }

}
