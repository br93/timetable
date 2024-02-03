package dev.timetable.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.timetable.domain.timetable.Lesson;
import dev.timetable.domain.timetable.Solution;
import dev.timetable.domain.timetable.Timeslot;
import dev.timetable.domain.timetable.dto.LessonRequest;
import dev.timetable.service.SolutionService;
import dev.timetable.service.TimeslotService;
import dev.timetable.service.TimetableService;
import dev.timetable.service.TokenService;
import dev.timetable.util.LessonMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("timetable")
@RequiredArgsConstructor
public class TimetableController {

    
    private final TokenService tokenService;
    private final TimeslotService timeslotService;
    private final SolutionService solutionService;
    private final TimetableService timetableService;
    private final LessonMapper lessonMapper;

    @PostMapping
    public Solution timetable(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient,
            @RequestBody List<@Valid LessonRequest> request) {

        String token = tokenService.getToken(authorizedClient);
        String user = tokenService.getUser(authorizedClient);

        List<Timeslot> timeslots = timeslotService.getValidTimeslots(user);
        List<Lesson> lessons = request.stream().map(lessonMapper::toLesson).collect(Collectors.toList());
       
        Solution solve = this.timetableService.solve("timetable", lessons, timeslots, user);    
        return this.solutionService.createSolution(solve, token, user);
    }

}