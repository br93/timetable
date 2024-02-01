package dev.timetable.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import dev.timetable.domain.timetable.Timeslot;

@Repository
public interface TimeslotRepository extends MongoRepository<Timeslot, String>{
    
}
