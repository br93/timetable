package dev.timetable.util;

import org.springframework.stereotype.Component;

@Component
public class MessageUtil {

    public String notFoundMessage(String entity, String field, String value){
        return String.format("%s with %s %s not found", entity, field, value);
    }
    
}
