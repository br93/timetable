package dev.timetable.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import dev.timetable.domain.timetable.Solution;

@Repository
public interface SolutionRepository extends MongoRepository<Solution, String>{
    
}
