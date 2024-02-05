package dev.timetable.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.timetable.domain.timetable.Lesson;
import dev.timetable.domain.timetable.dto.LessonRequest;

@SpringBootTest
class LessonMapperTest {

    @Autowired
    private LessonMapper lessonMapper;

    private Lesson mockLesson;
    private LessonRequest mockRequest;

    private static final String MOCK_SUBJECT = "TEST-SUBJECT";
    private static final int MOCK_LEVEL = 3;

    @BeforeEach
    void setup() {
        mockLesson = new Lesson(null, MOCK_SUBJECT, null, MOCK_LEVEL);
        mockRequest = new LessonRequest(MOCK_SUBJECT, MOCK_LEVEL);
    }

    @Test
    void toLessonShouldReturnLesson() {

        Lesson lesson = this.lessonMapper.toLesson(mockRequest);
        Assertions.assertAll(() -> Assertions.assertEquals(mockLesson.getSubject(), lesson.getSubject()),
                () -> Assertions.assertEquals(mockLesson.getLevel(), lesson.getLevel()));
    }
}
