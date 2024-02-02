package dev.timetable.service;

import org.springframework.stereotype.Service;

import dev.timetable.domain.timetable.Timeslot;
import dev.timetable.exception.EntityNotFoundException;
import dev.timetable.repository.TimeslotRepository;
import dev.timetable.util.MessageUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TimeslotService {

    private final TimeslotRepository timeslotRepository;
    private final MessageUtil messageUtil;

    public Timeslot createTimeslot(Timeslot timeslot) {
        return this.timeslotRepository.save(timeslot);
    }

    public Timeslot findTimeslotById(String id) {
        return this.timeslotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.notFoundMessage("Timeslot", "id", id)));
    }

    public Timeslot findMostRecentTimeslotByAuthor(String author) {
        return this.timeslotRepository.findByCreatedByOrderByCreatedAtDesc(author).stream().findFirst()
                .orElseThrow(
                        () -> new EntityNotFoundException(messageUtil.notFoundMessage("Timeslot", "author", author)));
    }
}
