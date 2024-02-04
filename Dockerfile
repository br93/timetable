FROM eclipse-temurin:21-jre-alpine
COPY target/timetable-0.0.1-SNAPSHOT.jar timetable.jar
CMD ["java", "-jar", "timetable.jar"]