package dev.timetable.controller;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.timefold.solver.core.api.solver.SolverManager;
import dev.timetable.domain.calendar.CalendarRequest;
import dev.timetable.domain.timetable.Lesson;
import dev.timetable.domain.timetable.Solution;
import dev.timetable.domain.timetable.Timeslot;
import dev.timetable.domain.timetable.Timetable;
import dev.timetable.domain.timetable.dto.LessonRequest;
import dev.timetable.service.CalendarService;
import dev.timetable.service.EventService;
import dev.timetable.service.SolutionService;
import dev.timetable.service.TimeslotService;
import dev.timetable.service.TokenService;
import dev.timetable.util.EventMapper;
import dev.timetable.util.LessonMapper;
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

    private final TimeslotService timeslotService;
    private final LessonMapper lessonMapper;
    private final SolutionService solutionService;

    @PostMapping
    public Solution timetable(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient,
            @RequestBody List<LessonRequest> request) throws InterruptedException, ExecutionException {

        var token = tokenService.getToken(authorizedClient);
        var user = tokenService.getUser(authorizedClient);

        var calendarRequest = new CalendarRequest("Test", "Description test", null);
        var calendarId = calendarService.createCalendar(token, calendarRequest);

        var timeslot = this.timeslotService.findMostRecentTimeslotByAuthor(user);
        List<Timeslot> timeslots = List.of(timeslot);

        List<Lesson> lessons = request.stream().map(requestObject -> {
            Lesson lesson = lessonMapper.toLesson(requestObject);
            lesson.setTimeslot(timeslot);
            return lesson;
        }).collect(Collectors.toList());
        
        Collections.shuffle(lessons);

        var problem = new Timetable("timetable", lessons, timeslots);
        var solved = solverManager.solve("id", problem).getFinalBestSolution();
        var solution = solutionService.createSolution(new Solution(null, solved.getLessons(), user));

        var solutionLessons = solution.getLessons();
        var list = solutionLessons.stream().map(eventMapper::toEventRequest).toList();

        list.forEach(event -> {
            eventService.createEvent(token, calendarId, event);
        });

        return solution;

    }
}