package dev.timetable.controller;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.timefold.solver.core.api.solver.SolverManager;
import dev.timetable.domain.calendar.CalendarRequest;
import dev.timetable.domain.timetable.Lesson;
import dev.timetable.domain.timetable.Timeslot;
import dev.timetable.domain.timetable.Timetable;
import dev.timetable.service.CalendarService;
import dev.timetable.service.EventService;
import dev.timetable.service.TokenService;
import dev.timetable.util.EventMapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("timetable")
@RequiredArgsConstructor
public class TimetableController {

    private final SolverManager<Timetable, String> solverManager;
    private final EventMapper eventMapper;
    private final CalendarService calendarService;
    private final EventService eventService;
    private final TokenService tokenService;
    
    @GetMapping
    public Timetable timetable(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient) throws InterruptedException, ExecutionException {

        var token = tokenService.getToken(authorizedClient);
        var calendarRequest = new CalendarRequest("Test", "Description test", null);
        var calendarId = calendarService.createCalendar(token, calendarRequest);

        List<Timeslot> timeslots = List.of(new Timeslot(LocalTime.of(18, 00), LocalTime.of(20, 00)));
       
        Lesson lesson1 = new Lesson(0L, "lesson1", timeslots.get(0), 1);
        Lesson lesson2 = new Lesson(1L, "lesson2", timeslots.get(0), 2);
        Lesson lesson3 = new Lesson(2L, "lesson3", timeslots.get(0), 3);
        Lesson lesson4 = new Lesson(3L, "lesson4", timeslots.get(0), 3);
        Lesson lesson5 = new Lesson(4L, "lesson5", timeslots.get(0), 2);

        List<Lesson> lessons = new ArrayList<>();
        lessons.addAll(List.of(lesson1, lesson2, lesson3, lesson4, lesson5));
        Collections.shuffle(lessons);

        var problem = new Timetable("timetable", lessons, timeslots);
        var solution = solverManager.solve("id", problem).getFinalBestSolution();

        var solutionLessons = solution.getLessons();
        var list = solutionLessons.stream().map(eventMapper::toEventRequest).toList();
        

        

        list.forEach(event -> {
            eventService.createEvent(token, calendarId, event);
        });

        return solution;



    }
}