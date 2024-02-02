package dev.timetable.service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import ai.timefold.solver.core.api.solver.SolverManager;
import dev.timetable.domain.timetable.Lesson;
import dev.timetable.domain.timetable.Solution;
import dev.timetable.domain.timetable.Timeslot;
import dev.timetable.domain.timetable.Timetable;
import dev.timetable.exception.SolverException;
import dev.timetable.util.MessageUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimetableService {

    private final SolverManager<Timetable, String> solverManager;
    private final MessageUtil messageUtil;

    public Solution solve(String name, List<Lesson> lessons, List<Timeslot> timeslot, String author) {
        Collections.shuffle(lessons);

        Timetable problem = new Timetable(name, lessons, timeslot);
        Timetable solved;

        try {
            solved = solverManager.solve("id", problem).getFinalBestSolution();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new SolverException(messageUtil.solverExceptionMessage(ex.getMessage()));
        } catch (ExecutionException ex) {
            throw new SolverException(messageUtil.solverExceptionMessage(ex.getMessage()));
        }

        return new Solution(null, solved.getLessons(), author);
    }
}
