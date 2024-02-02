package dev.timetable.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import dev.timetable.domain.timetable.Timeslot;

@Repository
public interface TimeslotRepository extends MongoRepository<Timeslot, String>{

    public List<Timeslot> findByCreatedByOrderByCreatedAtDesc(String createdBy);
}
