package dev.timetable.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import dev.timetable.domain.timetable.Timeslot;
import dev.timetable.exception.EntityNotFoundException;
import dev.timetable.repository.TimeslotRepository;
import dev.timetable.util.MessageUtil;

@SpringBootTest
class TimeslotServiceTest {

    @Autowired
    private TimeslotService timeslotService;

    @MockBean
    private TimeslotRepository timeslotRepository;

    private Timeslot mockTimeslot;
    private Timeslot mockTimeslotFirst;
    private Timeslot mockTimeslotLast;

    private static final String MOCK_ID = "MOCK-ID";
    private static final String MOCK_AUTHOR = "MOCK-AUTHOR";

    private static final String EXCEPTION_MESSAGE_ID = String.format("Timeslot with id %s not found", MOCK_ID);
    private static final String EXCEPTION_MESSAGE_AUTHOR = String.format("Timeslot with author %s not found",
            MOCK_AUTHOR);

    @BeforeEach
    void setup() {
        mockTimeslot = new Timeslot(LocalTime.of(15, 00, 00), LocalTime.of(17, 00, 00));
        mockTimeslotFirst = new Timeslot(LocalTime.of(15, 00, 00), LocalTime.of(16, 00, 00));
        mockTimeslotLast = new Timeslot(LocalTime.of(16, 00, 00), LocalTime.of(17, 00, 00));
    }

    @Test
    void createTimeslotShouldReturnTimeslot() {
        Mockito.when(this.timeslotRepository.save(any(Timeslot.class))).thenReturn(mockTimeslot);
        Assertions.assertEquals(mockTimeslot, this.timeslotService.createTimeslot(mockTimeslot));
    }

    @Test
    void findTimeslotByIdShouldReturnTimeslot() {
        Mockito.when(this.timeslotRepository.findById(anyString())).thenReturn(Optional.of(mockTimeslot));
        Assertions.assertEquals(mockTimeslot, this.timeslotService.findTimeslotById(MOCK_ID));
    }

    @Test
    void findTimeslotByIdShouldThrowEntityNotFoundIfNotFound() {
        Assertions.assertThrowsExactly(EntityNotFoundException.class,
                () -> this.timeslotService.findTimeslotById(MOCK_ID));
    }

    @Test
    void findTimeslotByIdShouldThrowExceptionWithCustomMessage() {
        Exception exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> this.timeslotService.findTimeslotById(MOCK_ID));

       Assertions.assertTrue(exception.getMessage().contains(EXCEPTION_MESSAGE_ID));
    }

    @Test
    void findMostRecentTimeslotByAuthorShouldReturnTimeslot() {
        Mockito.when(this.timeslotRepository.findByCreatedByOrderByCreatedAtDesc(anyString())).thenReturn(
                List.of(mockTimeslot));
        Assertions.assertEquals(mockTimeslot, this.timeslotService.findMostRecentTimeslotByAuthor(MOCK_AUTHOR));
    }

    @Test
    void findMostRecentTimeslotByAuthorShouldThrowEntityNotFoundIfNotFound() {
        Assertions.assertThrowsExactly(EntityNotFoundException.class,
                () -> this.timeslotService.findMostRecentTimeslotByAuthor(MOCK_AUTHOR));
    }

    @Test
    void findMostRecentTimeslotByAuthorShouldThrowExceptionWithCustomMessage() {
        Exception exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> this.timeslotService.findMostRecentTimeslotByAuthor(MOCK_AUTHOR));

       Assertions.assertTrue(exception.getMessage().contains(EXCEPTION_MESSAGE_AUTHOR));
    }

    @Test
    void getValidTimeslotsShouldReturnAListOfTimeslots() {
        Mockito.when(this.timeslotRepository.findByCreatedByOrderByCreatedAtDesc(anyString())).thenReturn(
                List.of(mockTimeslot));
        List<Timeslot> timeslots = this.timeslotService.getValidTimeslots(MOCK_AUTHOR);

        Assertions.assertAll(
                () -> Assertions.assertEquals(3, timeslots.size()),
                () -> Assertions
                        .assertTrue(timeslots.containsAll(List.of(mockTimeslot, mockTimeslotFirst, mockTimeslotLast))));

    }

    @Test
    void getValidTimeslotsShouldThrowEntityNotFoundIfNotFound() {
        Assertions.assertThrowsExactly(EntityNotFoundException.class,
                () -> this.timeslotService.getValidTimeslots(MOCK_AUTHOR));
    }

    @Test
    void getValidTimeslotsShouldThrowExceptionWithCustomMessage() {
        Exception exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> this.timeslotService.getValidTimeslots(MOCK_AUTHOR));

       Assertions.assertTrue(exception.getMessage().contains(EXCEPTION_MESSAGE_AUTHOR));
    }

}
