package dev.timetable.util;

import org.springframework.stereotype.Component;

import dev.timetable.rabbitmq.Message;

@Component
public class MessageUtil {

    public String notFoundMessage(String entity, String field, String value){
        return String.format("%s with %s %s not found", entity, field, value);
    }

    public Message eventMessage(String solutionId, String token, String author){
        return new Message(solutionId, token, author);
    }

    public String solverExceptionMessage(String message){
        return String.format("Problem not solved, error: %s", message);
    }
    
}
