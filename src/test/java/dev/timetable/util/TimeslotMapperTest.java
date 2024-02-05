package dev.timetable.util;

import java.time.Instant;
import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.timetable.domain.timetable.Timeslot;
import dev.timetable.domain.timetable.dto.TimeslotRequest;
import dev.timetable.domain.timetable.dto.TimeslotResponse;

@SpringBootTest
class TimeslotMapperTest {

    @Autowired
    private TimeslotMapper timeslotMapper;

    private Timeslot mockTimeslot;
    private TimeslotRequest mockRequest;
    private TimeslotResponse mockResponse;

    private static final String MOCK_AUTHOR = "TEST-AUTHOR";

    @BeforeEach
    void setup() {
        mockTimeslot = new Timeslot(LocalTime.of(15, 00, 00), LocalTime.of(17, 00, 00));
        mockTimeslot.setCreatedBy(MOCK_AUTHOR);

        mockRequest = new TimeslotRequest(LocalTime.of(15, 00, 00), LocalTime.of(17, 00, 00));
        mockResponse = new TimeslotResponse(LocalTime.of(15, 00, 00), LocalTime.of(17, 00, 00), MOCK_AUTHOR,
                Instant.now());
    }

    @Test
    void toTimeslotShouldReturnTimeslot() {
        Timeslot timeslot = this.timeslotMapper.toTimeslot(mockRequest, MOCK_AUTHOR);

        Assertions.assertAll(() -> Assertions.assertEquals(mockTimeslot.getStart(), timeslot.getStart()),
                () -> Assertions.assertEquals(mockTimeslot.getEnd(), timeslot.getEnd()));
    }

    @Test
    void toTimeslotResponseShouldReturnTimeslotResponse() {
        TimeslotResponse response = this.timeslotMapper.toTimeslotResponse(mockTimeslot);

        Assertions.assertAll(() -> Assertions.assertEquals(mockResponse.getStart(), response.getStart()),
                () -> Assertions.assertEquals(mockResponse.getEnd(), response.getEnd()),
                () -> Assertions.assertTrue(response.getCreatedBy().contains(MOCK_AUTHOR)));
    }
}
