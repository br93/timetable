package dev.timetable.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import dev.timetable.client.CalendarClient;
import dev.timetable.client.CalendarEventClient;
import dev.timetable.config.GoogleCalendarConfig.CalendarProperties;

@Configuration
public class RestClientConfig {

    @Bean
    RestClient restClient(CalendarProperties properties, RestClient.Builder builder) {
        return builder.baseUrl(properties.getBaseUrl()).build();
    }

    @Bean
    CalendarClient restCalendarClient(RestClient restClient) {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();

        return factory.createClient(CalendarClient.class);
    }

    @Bean
    CalendarEventClient restCalendarEventClient(RestClient restClient) {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();

        return factory.createClient(CalendarEventClient.class);
    }

}
