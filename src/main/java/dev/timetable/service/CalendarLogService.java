package dev.timetable.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.timetable.domain.calendar.CalendarLog;
import dev.timetable.exception.EntityNotFoundException;
import dev.timetable.repository.CalendarLogRepository;
import dev.timetable.util.MessageUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarLogService {

    private CalendarLogRepository calendarLogRepository;
    private MessageUtil messageUtil;

    public CalendarLog createCalendarLog(CalendarLog calendarLog) {
        return this.calendarLogRepository.save(calendarLog);
    }

    public CalendarLog findCalendarLogById(String id) {
        return this.calendarLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.notFoundMessage("Calendar log", "id", id)));
    }

    public List<CalendarLog> findAllCalendarLogByAuthor(String author){
        return this.calendarLogRepository.findAllByCreatedBy(author);
    }
}
