package guesto.event.service;

import guesto.event.dto.EventDTO;
import guesto.event.dto.EventResponseDTO;
import guesto.event.exception.EntityExistsException;
import guesto.event.exception.EventNotFoundException;
import guesto.event.model.Event;
import guesto.event.model.GuestList;
import guesto.event.repository.EventRepository;
import guesto.event.repository.GuestListRepository;
import guesto.user.model.User;
import guesto.user.repository.UserRepository;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class EventService {

    private final EventRepository eventRepository;
    private final GuestListRepository guestListRepository;
    private final UserRepository userRepository;

    @Inject
    public EventService(EventRepository eventRepository, GuestListRepository guestListRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.guestListRepository = guestListRepository;
        this.userRepository = userRepository;
    }

    public EventResponseDTO createEvent(EventDTO eventDTO, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));

        Event event = new Event();
        populateEventFromDTO(event, eventDTO);
        event.setCreatedBy(user.getId());
        event.setCreatedTime(LocalDateTime.now());

        GuestList guestList = new GuestList();
        guestList.setEvent(event);
        event.setGuestList(guestList);

        Event savedEvent = eventRepository.save(event);
        return convertToEventResponseDTO(savedEvent);
    }

    public List<EventResponseDTO> listEventsSorted(String sortField, boolean isAscending) {
        Stream<Event> eventStream = eventRepository.findAll().stream();

        if (sortField != null && !sortField.isEmpty()) {
            Comparator<Event> comparator = switch (sortField.toLowerCase()) {
                case "id" -> Comparator.comparing(Event::getId);
                case "eventname" -> Comparator.comparing(Event::getEventName);
                case "eventtime" -> Comparator.comparing(Event::getEventTime);
                case "maxguestlist" -> Comparator.comparing(Event::getMaxGuestList);
                case "price" -> Comparator.comparing(Event::getPrice);
                case "location" -> Comparator.comparing(Event::getLocation);
                default -> null;
            };

            if (comparator != null) {
                eventStream = isAscending ? eventStream.sorted(comparator) : eventStream.sorted(comparator.reversed());
            }
        }

        return eventStream.map(this::convertToEventResponseDTO).collect(Collectors.toList());
    }

    public Optional<EventResponseDTO> getEventById(Long eventId) {
        return eventRepository.findById(eventId).map(this::convertToEventResponseDTO);
    }

    public EventResponseDTO updateEvent(Long id, EventDTO eventDTO) {
        return eventRepository.findById(id).map(existingEvent -> {
            populateEventFromDTO(existingEvent, eventDTO);
            Event updatedEvent = eventRepository.update(existingEvent);
            return convertToEventResponseDTO(updatedEvent);
        }).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + id));
    }

    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + id));
        GuestList guestList = event.getGuestList();
        if (guestList != null) {
            guestListRepository.delete(guestList);
        }

        eventRepository.deleteById(id);
    }

    @Transactional
    public void assignUserToEvent(Long userId, Long eventId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Event> eventOpt = eventRepository.findById(eventId);

        if (userOpt.isPresent() && eventOpt.isPresent()) {
            Event event = eventOpt.get();
            Long existingUserId = userOpt.get().getId();

            if (event.getAssignedUserIds().contains(existingUserId)) {
                throw new EntityExistsException("User is already assigned to the event");
            }

            event.assignUserId(existingUserId);
            eventRepository.save(event);
        } else {
            throw new EventNotFoundException("User or Event not found");
        }
    }

    @Transactional
    public void unassignUserFromEvent(Long userId, Long eventId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Event> eventOpt = eventRepository.findById(eventId);

        if (userOpt.isPresent() && eventOpt.isPresent()) {
            Event event = eventOpt.get();
            event.unassignUserId(userId);
            eventRepository.save(event);
        } else {
            throw new EventNotFoundException("User or Event not found");
        }
    }


    private void populateEventFromDTO(Event event, EventDTO dto) {
        event.setEventName(dto.getEventName());
        event.setEventTime(dto.getEventTime());
        event.setMaxGuestList(dto.getMaxGuestList());
        event.setPrice(dto.getPrice());
        event.setLocation(dto.getLocation());
    }

    private EventResponseDTO convertToEventResponseDTO(Event event) {

        EventResponseDTO dto = new EventResponseDTO(event.getId(), event.getEventName(), event.getEventTime(), event.getMaxGuestList(), event.getPrice(), event.getLocation(), event.getCreatedBy(), event.getCreatedTime());
        dto.setCheckedInGuestsCount(event.getCheckedInGuestsCount());
        dto.setTotalGuestCount(event.getTotalGuestCount());
        dto.setAssignedUserIds(event.getAssignedUserIds());
        return dto;
    }

}
