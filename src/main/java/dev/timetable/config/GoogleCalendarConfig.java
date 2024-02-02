package dev.timetable.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
public class GoogleCalendarConfig {

    @Bean
    @ConfigurationProperties("google.calendar")
    CalendarProperties calendarProperties() {
        return new CalendarProperties();
    }

    @Setter
    @Getter
    public static class CalendarProperties {
        private String timeZone;
        private String recurrence;
        private String reminder;
        private String baseUrl;
        private String calendarName;
        private String calendarDescription;
    }

}
