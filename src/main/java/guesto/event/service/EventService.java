package guesto.event.service;

import guesto.event.dto.EventDTO;
import guesto.event.exception.EventNotFoundException;
import guesto.event.model.Event;
import guesto.event.repository.EventRepository;
import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class EventService {

    private final EventRepository eventRepository;

    @Inject
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(EventDTO eventDTO, Authentication authentication) {
        Event event = new Event();
        populateEventFromDTO(event, eventDTO);
        event.setCreatedBy(authentication.getName());
        event.setCreatedTime(LocalDateTime.now());
        return eventRepository.save(event);
    }

    public List<Event> listEvents() {
        return eventRepository.findAll();
    }

    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        return eventRepository.findById(id)
                .map(existingEvent -> {
                    populateEventFromDTO(existingEvent, eventDTO);
                    Event updatedEvent = eventRepository.update(existingEvent);
                    return convertToEventDTO(updatedEvent);
                }).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + id));
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    private void populateEventFromDTO(Event event, EventDTO dto) {
        event.setEventName(dto.getEventName());
        event.setEventTime(dto.getEventTime());
        event.setMaxGuestList(dto.getMaxGuestList());
        event.setPrice(dto.getPrice());
        event.setLocation(dto.getLocation());
    }

    private EventDTO convertToEventDTO(Event event) {
        return new EventDTO(event.getEventName(), event.getEventTime(), event.getMaxGuestList(), event.getPrice(), event.getLocation());
    }
}
