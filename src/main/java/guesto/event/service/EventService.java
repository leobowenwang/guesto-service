package guesto.event.service;

import guesto.event.dto.EventDTO;
import guesto.event.dto.GuestDTO;
import guesto.event.exception.EventNotFoundException;
import guesto.event.model.Event;
import guesto.event.model.Guest;
import guesto.event.model.GuestList;
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

    public boolean checkInGuest(Long eventId, String guestName) {
        return eventRepository.findById(eventId)
                .flatMap(event -> findGuestByName(event.getGuestList().getGuests(), guestName)
                        .filter(guest -> !guest.isCheckedIn())
                        .map(guest -> {
                            guest.setCheckedIn(true);
                            event.incrementCheckedInGuests();
                            eventRepository.update(event);
                            return true;
                        })).orElse(false);
    }

    private Optional<Guest> findGuestByName(List<Guest> guestList, String guestName) {
        return guestList.stream()
                .filter(guest -> (guest.getFirstName() + " " + guest.getLastName()).equals(guestName))
                .findFirst();
    }

    private EventDTO convertToEventDTO(Event event) {
        return new EventDTO(event.getEventName(), event.getEventTime(), event.getMaxGuestList(), event.getPrice(), event.getLocation());
    }

    public GuestDTO addGuestToEvent(Long eventId, GuestDTO guestDTO) {
        return eventRepository.findById(eventId)
                .map(event -> {
                    GuestList guestList = Optional.ofNullable(event.getGuestList())
                            .orElseGet(() -> new GuestList(event));
                    Guest guest = convertToEntity(guestDTO);
                    guestList.getGuests().add(guest);
                    guest.setGuestList(guestList);
                    eventRepository.update(event);
                    return convertToGuestDTO(guest);
                }).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
    }

    private void populateEventFromDTO(Event event, EventDTO dto) {
        event.setEventName(dto.getEventName());
        event.setEventTime(dto.getEventTime());
        event.setMaxGuestList(dto.getMaxGuestList());
        event.setPrice(dto.getPrice());
        event.setLocation(dto.getLocation());
    }

    private Guest convertToEntity(GuestDTO guestDTO) {
        return new Guest(guestDTO.id(), guestDTO.firstName(), guestDTO.lastName(), guestDTO.isCheckedIn(), null, guestDTO.getAdditionalGuests());
    }

    private GuestDTO convertToGuestDTO(Guest guest) {
        return new GuestDTO(guest.getId(), guest.getFirstName(), guest.getLastName(), guest.isCheckedIn(), guest.getAdditionalGuests());
    }
}

