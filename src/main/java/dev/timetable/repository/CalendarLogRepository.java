package dev.timetable.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import dev.timetable.domain.calendar.CalendarLog;

@Repository
public interface CalendarLogRepository extends MongoRepository<CalendarLog, String> {
    
    List<CalendarLog> findAllByCreatedBy(String createdBy);
}
