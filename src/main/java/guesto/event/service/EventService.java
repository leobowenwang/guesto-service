package guesto.event.service;

import guesto.event.dto.EventDTO;
import guesto.event.model.Event;
import guesto.event.model.Guest;
import guesto.event.repository.EventRepository;
import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Singleton
public class EventService {

    private final EventRepository eventRepository;

    @Inject
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(EventDTO eventDTO, Authentication authentication) {
        Event event = new Event();
        event.setEventName(eventDTO.getEventName());
        event.setEventTime(eventDTO.getEventTime());
        event.setMaxGuestList(eventDTO.getMaxGuestList());
        event.setPrice(eventDTO.getPrice());
        event.setLocation(eventDTO.getLocation());
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
                    existingEvent.setEventName(eventDTO.getEventName());
                    existingEvent.setEventTime(eventDTO.getEventTime());
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

    public boolean checkInGuest(Long eventId, String guestName) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            Optional<Guest> guestOptional = findGuestByName(event.getGuestList().getGuests(), guestName);

            if (guestOptional.isPresent()) {
                Guest guest = guestOptional.get();
                if (!guest.isCheckedIn()) {
                    guest.setCheckedIn(true);
                    event.incrementCheckedInGuests();
                    eventRepository.update(event);
                    return true;
                }
            }
        }
        return false;
    }

    private Optional<Guest> findGuestByName(List<Guest> guestList, String guestName) {
        return guestList.stream()
                .filter(guest -> guest.getFirstName().concat(" ").concat(guest.getLastName()).equals(guestName))
                .findFirst();
    }

    private EventDTO convertToDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setEventName(event.getEventName());
        dto.setEventTime(event.getEventTime());
        dto.setMaxGuestList(event.getMaxGuestList());
        dto.setPrice(event.getPrice());
        dto.setLocation(event.getLocation());
        return dto;
    }
}