package dev.timetable.util;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

import org.springframework.stereotype.Component;

@Component
public class TimeUtil {

    public LocalDateTime convertTime(DayOfWeek evaluatedDay, LocalTime time) {
        var startOfWeek = this.startOfWeek();
        var dayOfMonth = this.dayOfMonth(startOfWeek, evaluatedDay);
    
        return LocalDateTime.of(dayOfMonth.getYear(), dayOfMonth.getMonthValue(), dayOfMonth.getDayOfMonth(), time.getHour(),
                time.getMinute());

    }

    private LocalDateTime startOfWeek() {
        return LocalDateTime.now()
                .with(LocalTime.MIN)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
    }

    private LocalDateTime dayOfMonth(LocalDateTime startOfWeek, DayOfWeek evaluatedDay) {
        return startOfWeek.plusDays(evaluatedDay.getValue());
    }


}
