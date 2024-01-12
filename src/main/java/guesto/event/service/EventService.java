package guesto.event.service;

import guesto.event.dto.EventDTO;
import guesto.event.dto.EventResponseDTO;
import guesto.event.exception.EventNotFoundException;
import guesto.event.model.Event;
import guesto.event.model.GuestList;
import guesto.event.repository.EventRepository;
import guesto.event.repository.GuestListRepository;
import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class EventService {

    private final EventRepository eventRepository;
    private final GuestListRepository guestListRepository;

    @Inject
    public EventService(EventRepository eventRepository, GuestListRepository guestListRepository) {
        this.eventRepository = eventRepository;
        this.guestListRepository = guestListRepository;
    }

    public EventResponseDTO createEvent(EventDTO eventDTO, Authentication authentication) {
        Event event = new Event();
        populateEventFromDTO(event, eventDTO);
        event.setCreatedBy(authentication.getName());
        event.setCreatedTime(LocalDateTime.now());

        GuestList guestList = new GuestList();
        guestList.setEvent(event);
        event.setGuestList(guestList);

        Event savedEvent = eventRepository.save(event);
        return convertToEventResponseDTO(savedEvent);
    }

    public List<EventResponseDTO> listEvents() {
        return eventRepository.findAll().stream()
                .map(this::convertToEventResponseDTO)
                .collect(Collectors.toList());
    }


    public EventResponseDTO updateEvent(Long id, EventDTO eventDTO) {
        return eventRepository.findById(id)
                .map(existingEvent -> {
                    populateEventFromDTO(existingEvent, eventDTO);
                    Event updatedEvent = eventRepository.update(existingEvent);
                    return convertToEventResponseDTO(updatedEvent);
                })
                .orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + id));
    }

    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(() ->
                new EventNotFoundException("Event not found with ID: " + id));
        GuestList guestList = event.getGuestList();
        if (guestList != null) {
            guestListRepository.delete(guestList);
        }

        eventRepository.deleteById(id);
    }


    private void populateEventFromDTO(Event event, EventDTO dto) {
        event.setEventName(dto.getEventName());
        event.setEventTime(dto.getEventTime());
        event.setMaxGuestList(dto.getMaxGuestList());
        event.setPrice(dto.getPrice());
        event.setLocation(dto.getLocation());
    }

    private EventResponseDTO convertToEventResponseDTO(Event event) {
        return new EventResponseDTO(
                event.getId(),
                event.getEventName(),
                event.getEventTime(),
                event.getMaxGuestList(),
                event.getPrice(),
                event.getLocation()
        );
    }
}
