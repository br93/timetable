package dev.timetable.domain.event;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventReminder {
    
    private List<ItemReminder> overrides;
    private Boolean useDefault;
}
