package dev.timetable.service;

import java.util.List;

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

    public List<Timeslot> getValidTimeslots(String author) {
        Timeslot timeslot = this.findMostRecentTimeslotByAuthor(author);
        return List.of(timeslot, new Timeslot(timeslot.getStart(), timeslot.getEnd().minusHours(1L)),
                new Timeslot(timeslot.getStart().plusHours(1L), timeslot.getEnd()));
    }
}
