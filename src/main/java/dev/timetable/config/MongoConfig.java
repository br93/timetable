package dev.timetable.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
public class MongoConfig {

    @Bean
    @ConfigurationProperties(prefix = "mongo")
    MongoProperties mongoProperties() {
        return new MongoProperties();
    }

    @Setter
    @Getter
    public static class MongoProperties {
        private String host;
        private String port;
        private String user;
        private String password;
    }
    
}
