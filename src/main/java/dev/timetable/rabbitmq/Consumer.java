package dev.timetable.rabbitmq;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import dev.timetable.domain.calendar.CalendarRequest;
import dev.timetable.domain.event.CalendarEventRequest;
import dev.timetable.domain.timetable.Lesson;
import dev.timetable.service.CalendarEventService;
import dev.timetable.service.CalendarService;
import dev.timetable.service.SolutionService;
import dev.timetable.util.CalendarEventMapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Consumer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CalendarService calendarService;
    private final CalendarEventMapper eventMapper;
    private final CalendarEventService eventService;
    private final SolutionService solutionService;

    @RabbitListener(queues = "timetable-emit")
    public void receive(@Payload Message message) {

        logger.info("Message received: {}", message.solutionId());

        String calendarId;

        try {
            calendarId = this.calendarService.createCalendar(message.token(), new CalendarRequest());
        } catch (RuntimeException ex) {
            logger.info(ex.getMessage());
            return;
        }

        List<Lesson> lessons = this.solutionService.findSolutionById(message.solutionId()).getLessons();
        List<CalendarEventRequest> list = lessons.stream().map(eventMapper::toCalendarEventRequest).toList();

        this.postEvents(list, message.token(), calendarId);
    }

    private List<String> postEvents(List<CalendarEventRequest> list, String token, String calendarId) {
        List<String> eventIds = new ArrayList<>();

        list.forEach(event -> {
            String eventId;
            try {
                eventId = eventService.createEvent(token, calendarId, event);
                eventIds.add(eventId);
            } catch (RuntimeException ex) {
                logger.info(ex.getMessage());
                return;
            }

        });

        return eventIds;
    }
}
