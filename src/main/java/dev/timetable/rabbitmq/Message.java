package dev.timetable.rabbitmq;

public record Message(String solutionId, String token, String author) {
}
