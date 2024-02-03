package dev.timetable.validation;

import java.time.Duration;

import dev.timetable.domain.timetable.dto.TimeslotRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TimeValidator implements ConstraintValidator<TimeslotValid, TimeslotRequest>{

    @Override
    public boolean isValid(TimeslotRequest value, ConstraintValidatorContext context) {
        if (value == null) 
            return true;
        

        if (value.getStart() == null || value.getEnd() == null)
            return false;
        
        boolean isStartBeforeEnd = value.getStart().isBefore(value.getEnd());
        boolean isDurationValid = Duration.between(value.getStart(), value.getEnd()).toHours() == 2;

        return isStartBeforeEnd && isDurationValid;
    }
    
}
