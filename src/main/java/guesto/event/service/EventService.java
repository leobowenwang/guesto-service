package guesto.event.service;

import guesto.event.dto.EventDTO;
import guesto.event.model.Event;
import guesto.event.repository.EventRepository;
import guesto.user.model.Role;
import guesto.user.model.User;
import guesto.user.service.UserService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class EventService {

    private final EventRepository eventRepository;
    private final UserService userService;

    @Inject
    public EventService(EventRepository eventRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
    }

    public Event createEvent(EventDTO eventDTO) {

        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDate(eventDTO.getEventDate());
        event.setMaxGuestList(eventDTO.getMaxGuestList());
        event.setPrice(eventDTO.getPrice());
        event.setLocation(eventDTO.getLocation());

        return eventRepository.save(event);
    }

    public List<Event> listEvents() {
        return eventRepository.findAll();
    }

    public EventDTO updateEvent(Long id, EventDTO eventDTO) {

        return eventRepository.findById(id)
                .map(existingEvent -> {
                    existingEvent.setName(eventDTO.getName());
                    existingEvent.setDate(eventDTO.getEventDate());
                    existingEvent.setMaxGuestList(eventDTO.getMaxGuestList());
                    existingEvent.setPrice(eventDTO.getPrice());
                    existingEvent.setLocation(eventDTO.getLocation());
                    Event updatedEvent = eventRepository.update(existingEvent);
                    return convertToDTO(updatedEvent);
                }).orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    private EventDTO convertToDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setName(event.getName());
        dto.setEventDate(event.getDate());
        dto.setMaxGuestList(event.getMaxGuestList());
        dto.setPrice(event.getPrice());
        dto.setLocation(event.getLocation());
        return dto;
    }
}