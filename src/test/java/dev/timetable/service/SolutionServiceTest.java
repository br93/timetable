package dev.timetable.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import dev.timetable.domain.timetable.Solution;
import dev.timetable.exception.EntityNotFoundException;
import dev.timetable.rabbitmq.Message;
import dev.timetable.rabbitmq.Producer;
import dev.timetable.repository.SolutionRepository;

@SpringBootTest
class SolutionServiceTest {
    
    @Autowired
    private SolutionService solutionService;

    @MockBean
    private SolutionRepository solutionRepository;

    @MockBean
    private Producer producer;

    private Solution mockSolution;

    private static final String MOCK_ID = "MOCK-ID";
    private static final String MOCK_AUTHOR = "MOCK-AUTHOR";
    private static final String MOCK_TOKEN = "MOCK-TOKEN";

    private static final String EXCEPTION_MESSAGE_ID = String.format("Solution with id %s not found", MOCK_ID);

    @BeforeEach
    void setup() {

        mockSolution = new Solution(MOCK_ID, new ArrayList<>(), MOCK_AUTHOR);

    }

    @Test
    void createSolutionShouldReturnSolution(){
        Mockito.when(this.solutionRepository.save(any(Solution.class))).thenReturn(mockSolution);
        Mockito.doNothing().when(this.producer).send(any(Message.class));
        Assertions.assertEquals(mockSolution, this.solutionService.createSolution(mockSolution, MOCK_TOKEN, MOCK_AUTHOR));
    }

    @Test
    void findSolutionByIdShouldReturnSolution(){
        Mockito.when(this.solutionRepository.findById(anyString())).thenReturn(Optional.of(mockSolution));
        Assertions.assertEquals(mockSolution, this.solutionService.findSolutionById(MOCK_ID));
    }

    @Test
    void findSolutionByIdShouldThrowEntityNotFoundIfNotFound(){
        Assertions.assertThrowsExactly(EntityNotFoundException.class, () -> this.solutionService.findSolutionById(MOCK_ID));
    }

    @Test
    void findSolutionByIdShouldThrowExceptionWithCustomMessage(){
        Exception exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> this.solutionService.findSolutionById(MOCK_ID));

       Assertions.assertTrue(exception.getMessage().contains(EXCEPTION_MESSAGE_ID));
    }
}
