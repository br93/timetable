package dev.timetable.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import dev.timetable.container.MongoDBTestContainer;
import dev.timetable.domain.timetable.Timeslot;

@DataMongoTest
class TimeslotRepositoryTest extends MongoDBTestContainer{

    @Autowired
    private TimeslotRepository timeslotRepository;

    private Timeslot mockTimeslot1;
    private Timeslot mockTimeslot2;

    private final String owner = UUID.randomUUID().toString();

    @AfterEach
    void cleanup() {
        timeslotRepository.deleteAll();
    }

    @BeforeEach()
    void setup() {

        mockTimeslot1 = new Timeslot(LocalTime.of(15, 00, 00), LocalTime.of(17, 00, 00));
        mockTimeslot2 = new Timeslot(LocalTime.of(21, 00, 00), LocalTime.of(23, 00, 00));

        mockTimeslot1.setCreatedAt(Instant.now());
        mockTimeslot1.setCreatedBy(owner);

        mockTimeslot2.setCreatedAt(Instant.now());
        mockTimeslot2.setCreatedBy(owner);

        timeslotRepository.save(mockTimeslot1);
        timeslotRepository.save(mockTimeslot2);
    }

    @Test
    void findByCreatedByOrderByCreatedAtDescShoudlReturnTimeslots() {

        List<Timeslot> timeslots = timeslotRepository.findByCreatedByOrderByCreatedAtDesc(owner);
        assertEquals(2, timeslots.size());
    }
}
