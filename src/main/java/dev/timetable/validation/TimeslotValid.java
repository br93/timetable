package dev.timetable.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimeValidator.class)
public @interface TimeslotValid {

    String message() default "Invalid timeslot: start time must be before end time and the duration must be 2 hours.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
}
