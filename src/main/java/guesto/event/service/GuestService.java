package guesto.event.service;

import guesto.event.dto.GuestDTO;
import guesto.event.dto.GuestResponseDTO;
import guesto.event.exception.EventNotFoundException;
import guesto.event.exception.GuestNotFoundException;
import guesto.event.model.Guest;
import guesto.event.model.GuestList;
import guesto.event.repository.EventRepository;
import guesto.event.repository.GuestListRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class GuestService {

    private final EventRepository eventRepository;
    private final GuestListRepository guestListRepository;

    @Inject
    public GuestService(EventRepository eventRepository, GuestListRepository guestListRepository) {
        this.eventRepository = eventRepository;
        this.guestListRepository = guestListRepository;
    }


    public boolean checkInGuest(Long eventId, Long guestId) {
        return eventRepository.findById(eventId).map(event -> {
            GuestList guestList = guestListRepository.findByEventId(event.getId()).orElse(new GuestList(event));
            Optional<Guest> guestToCheckIn = guestList.getGuestList().stream().filter(guest -> guest.getId().equals(guestId)).findFirst();

            if (guestToCheckIn.isPresent() && !guestToCheckIn.get().isCheckedIn()) {
                guestToCheckIn.get().setCheckedIn(true);
                event.incrementCheckedInGuests();
                guestListRepository.update(guestList); // Update guest list
                eventRepository.update(event);
                return true;
            }
            return false;
        }).orElse(false);
    }

    public GuestResponseDTO addGuestToEvent(Long eventId, GuestDTO guestDTO) {
        return eventRepository.findById(eventId).map(event -> {
            GuestList guestList = guestListRepository.findByEventId(event.getId()).orElseGet(() -> new GuestList(event));
            List<Guest> guests = Optional.ofNullable(guestList.getGuestList()).orElseGet(ArrayList::new);

            Guest guest = convertToEntity(guestDTO);
            guests.add(guest);
            guest.setGuestList(guestList);
            guestList.setGuests(guests);

            guestListRepository.update(guestList);
            eventRepository.update(event);
            return convertToGuestDTO(guest);
        }).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
    }

    public List<GuestResponseDTO> listAllGuests(Long eventId) {
        return eventRepository.findById(eventId).map(event -> guestListRepository.findByEventId(event.getId()).map(GuestList::getGuestList).orElse(Collections.emptyList())
                .stream().map(this::convertToGuestDTO).collect(Collectors.toList())).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
    }


    public GuestResponseDTO updateGuestInEvent(Long eventId, Long guestId, GuestDTO updatedGuestDTO) {
        return eventRepository.findById(eventId).map(event -> {
            GuestList guestList = guestListRepository.findByEventId(event.getId()).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));

            Optional<Guest> guestToUpdate = guestList.getGuestList().stream().filter(guest -> guest.getId().equals(guestId)).findFirst();

            if (guestToUpdate.isPresent()) {
                updateGuestFromDTO(guestToUpdate.get(), updatedGuestDTO);
                guestListRepository.update(guestList);
                eventRepository.update(event);
                return convertToGuestDTO(guestToUpdate.get());
            } else {
                throw new GuestNotFoundException("Guest not found with ID: " + guestId);
            }
        }).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
    }


    public boolean deleteGuestFromEvent(Long eventId, Long guestId) {
        return eventRepository.findById(eventId).map(event -> {
            GuestList guestList = guestListRepository.findByEventId(event.getId()).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));

            boolean removed = guestList.getGuestList().removeIf(guest -> guest.getId().equals(guestId));
            if (removed) {
                guestListRepository.update(guestList);
                eventRepository.update(event);
            }

            return removed;
        }).orElse(false);
    }


    private void updateGuestFromDTO(Guest guest, GuestDTO guestDTO) {
        guest.setFirstName(guestDTO.firstName());
        guest.setLastName(guestDTO.lastName());
        guest.setAdditionalGuests(guestDTO.getAdditionalGuests());
        guest.setComment(guestDTO.getComment());
        guest.setCustomPrice(guestDTO.getCustomPrice());
    }

    private Guest convertToEntity(GuestDTO guestDTO) {
        return new Guest(guestDTO.firstName(), guestDTO.lastName(), false, null, guestDTO.getAdditionalGuests(), guestDTO.getComment(), guestDTO.getCustomPrice());
    }

    private GuestResponseDTO convertToGuestDTO(Guest guest) {
        GuestResponseDTO dto = new GuestResponseDTO();
        dto.setId(guest.getId());
        dto.setFirstName(guest.getFirstName());
        dto.setLastName(guest.getLastName());
        dto.setAdditionalGuests(guest.getAdditionalGuests());
        dto.setComment(guest.getComment());
        dto.setCustomPrice(guest.getCustomPrice());
        return dto;
    }

}