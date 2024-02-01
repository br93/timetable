package dev.timetable.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import dev.timetable.domain.calendar.CalendarLog;

@Repository
public interface CalendarLogRepository extends MongoRepository<CalendarLog, String> {
    
}
