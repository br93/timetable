package dev.timetable.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.timetable.domain.timetable.Timeslot;
import dev.timetable.domain.timetable.dto.TimeslotRequest;
import dev.timetable.domain.timetable.dto.TimeslotResponse;
import dev.timetable.service.TimeslotService;
import dev.timetable.util.TimeslotMapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("timeslots")
@RequiredArgsConstructor
public class TimeslotController {

    private final TimeslotMapper timeslotMapper;
    private final TimeslotService timeslotService;

    @PostMapping
    public ResponseEntity<TimeslotResponse> createTimeslot(
            @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient,
            @RequestBody TimeslotRequest request) {

        String user = authorizedClient.getPrincipalName();
        Timeslot timeslot = this.timeslotMapper.toTimeslot(request, user);
        Timeslot newTimeslot = this.timeslotService.createTimeslot(timeslot);
        TimeslotResponse response = this.timeslotMapper.toTimeslotResponse(newTimeslot);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
