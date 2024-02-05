package dev.timetable.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ai.timefold.solver.core.api.solver.SolverJob;
import ai.timefold.solver.core.api.solver.SolverManager;
import dev.timetable.domain.timetable.Lesson;
import dev.timetable.domain.timetable.Solution;
import dev.timetable.domain.timetable.Timeslot;
import dev.timetable.domain.timetable.Timetable;
import dev.timetable.exception.SolverException;

@SpringBootTest
class TimetableServiceTest {
    
    @Autowired
    private TimetableService timetableService;

    @MockBean
    private SolverManager<Timetable, String> mockSolverManager;

    private static final String MOCK_NAME = "TEST-TIMETABLE";
    private static final String MOCK_AUTHOR = "TEST-AUTHOR";

    private List<Lesson> lessons;
    private List<Lesson> solvedLessons;
    private List<Timeslot> timeslots; 

    private Solution solution;

    private static final String MOCK_ERROR = "TEST-ERROR";
    private static final String EXCEPTION_MESSAGE = String.format("Problem not solved, error: %s", MOCK_ERROR);

    @BeforeEach
    void setup() {

       lessons = Arrays.asList(new Lesson(), new Lesson(), new Lesson());
       
       Lesson solvedLesson = new Lesson();
       solvedLesson.setDayOfWeek(DayOfWeek.MONDAY);

       solvedLessons = Arrays.asList(new Lesson(), solvedLesson, new Lesson());

       timeslots = Arrays.asList(new Timeslot());
       solution = new Solution(null, solvedLessons, MOCK_AUTHOR);

    }

    @Test
    void solveShouldReturnSolution() throws InterruptedException, ExecutionException {
        
        Timetable solved = new Timetable(MOCK_NAME, solvedLessons, timeslots);
        Mockito.when(mockSolverManager.solve(any(), any())).thenReturn(mock(SolverJob.class));
        Mockito.when(mockSolverManager.solve(any(), any()).getFinalBestSolution()).thenReturn(solved);

        Assertions.assertEquals(solution, this.timetableService.solve(MOCK_NAME, lessons, timeslots, MOCK_AUTHOR));  
    }

    @Test
    void solveShouldThrowSolverExceptionWhenSolverFails() throws InterruptedException, ExecutionException {
        Mockito.when(mockSolverManager.solve(any(), any())).thenReturn(mock(SolverJob.class));
        Mockito.when(mockSolverManager.solve(any(), any()).getFinalBestSolution()).thenThrow(new InterruptedException(MOCK_ERROR));
        Assertions.assertThrowsExactly(SolverException.class, () -> timetableService.solve(MOCK_NAME, lessons, timeslots, MOCK_AUTHOR));
    }

    @Test
    void solveShouldThrowSolverExceptionWithCustomMessage() throws InterruptedException, ExecutionException {
        Mockito.when(mockSolverManager.solve(any(), any())).thenReturn(mock(SolverJob.class));
        Mockito.when(mockSolverManager.solve(any(), any()).getFinalBestSolution()).thenThrow(new InterruptedException(MOCK_ERROR));
        Exception exception = Assertions.assertThrows(SolverException.class,
                () -> timetableService.solve(MOCK_NAME, lessons, timeslots, MOCK_AUTHOR));

        Assertions.assertTrue(exception.getMessage().contains(EXCEPTION_MESSAGE));
       
    }

}
