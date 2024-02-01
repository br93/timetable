package dev.timetable.util;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Component;

import dev.timetable.config.GoogleCalendarConfig.CalendarProperties;
import dev.timetable.domain.event.EventDateTime;
import dev.timetable.domain.event.EventReminder;
import dev.timetable.domain.event.EventRequest;
import dev.timetable.domain.event.ItemReminder;
import dev.timetable.domain.timetable.Lesson;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final CalendarProperties properties;
    private final TimeUtil timeUtil;

    public EventRequest toEventRequest(Lesson lesson) {

        return new EventRequest(this.eventDateTimeBuilder(lesson.getDayOfWeek(), lesson.getTimeslot().getStart()),
                this.eventDateTimeBuilder(lesson.getDayOfWeek(), lesson.getTimeslot().getEnd()),
                this.recurrenceBuilder(), lesson.getSubject(),
                this.eventReminderBuilder());
    }

    private EventReminder eventReminderBuilder() {
        String[] reminders = properties.getReminder().split(",");
        ItemReminder email = new ItemReminder(reminders[0], 24 * 60);
        ItemReminder popup = new ItemReminder(reminders[1], 10);

        return new EventReminder(List.of(email, popup), false);
    }

    private EventDateTime eventDateTimeBuilder(DayOfWeek dayOfWeek, LocalTime localTime) {
        var localDateTime = timeUtil.convertTime(dayOfWeek, localTime);
        return new EventDateTime(localDateTime, properties.getTimeZone());
    }

    private List<String> recurrenceBuilder() {
        return List.of(properties.getRecurrence());
    }

}
