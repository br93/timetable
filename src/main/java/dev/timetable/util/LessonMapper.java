package dev.timetable.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

import dev.timetable.domain.timetable.Lesson;
import dev.timetable.domain.timetable.dto.LessonRequest;

@Component
public class LessonMapper {

    public Lesson toLesson(LessonRequest dto) {

        String id = UUID.randomUUID().toString();

        return new Lesson(id, dto.getSubject(), null, dto.getLevel());
    }
}
