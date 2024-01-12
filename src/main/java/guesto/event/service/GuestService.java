package guesto.event.service;

import guesto.event.dto.GuestDTO;
import guesto.event.exception.EventNotFoundException;
import guesto.event.exception.GuestNotFoundException;
import guesto.event.model.Guest;
import guesto.event.model.GuestList;
import guesto.event.repository.EventRepository;
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

    @Inject
    public GuestService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public boolean checkInGuest(Long eventId, Long guestId) {
        return eventRepository.findById(eventId).map(event -> {
            GuestList guestList = Optional.ofNullable(event.getGuestList()).orElse(new GuestList(event));
            Optional<Guest> guestToCheckIn = guestList.getGuestList().stream().filter(guest -> guest.getId().equals(guestId)).findFirst();

            if (guestToCheckIn.isPresent() && !guestToCheckIn.get().isCheckedIn()) {
                guestToCheckIn.get().setCheckedIn(true);
                event.incrementCheckedInGuests();
                eventRepository.update(event);
                return true;
            }
            return false;
        }).orElse(false);
    }

    public GuestDTO addGuestToEvent(Long eventId, GuestDTO guestDTO) {
        return eventRepository.findById(eventId).map(event -> {
            GuestList guestList = Optional.ofNullable(event.getGuestList()).orElseGet(() -> new GuestList(event));
            List<Guest> guests = Optional.ofNullable(guestList.getGuestList()).orElseGet(ArrayList::new);
            Guest guest = convertToEntity(guestDTO);
            guests.add(guest);
            guest.setGuestList(guestList);
            guestList.setGuests(guests);

            eventRepository.update(event);
            return convertToGuestDTO(guest);
        }).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
    }


    public List<GuestDTO> listAllGuests(Long eventId) {
        return eventRepository.findById(eventId).map(event -> Optional.ofNullable(event.getGuestList()).map(GuestList::getGuestList).orElse(Collections.emptyList()).stream().map(this::convertToGuestDTO).collect(Collectors.toList())).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
    }

    public GuestDTO updateGuestInEvent(Long eventId, Long guestId, GuestDTO updatedGuestDTO) {
        return eventRepository.findById(eventId).map(event -> {
            Optional<Guest> guestToUpdate = event.getGuestList().getGuestList().stream().filter(guest -> guest.getId().equals(guestId)).findFirst();

            if (guestToUpdate.isPresent()) {
                updateGuestFromDTO(guestToUpdate.get(), updatedGuestDTO);
                eventRepository.update(event);
                return convertToGuestDTO(guestToUpdate.get());
            } else {
                throw new GuestNotFoundException("Guest not found with ID: " + guestId);
            }
        }).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
    }


    public boolean deleteGuestFromEvent(Long eventId, Long guestId) {
        return eventRepository.findById(eventId).map(event -> {
            boolean removed = event.getGuestList().getGuestList().removeIf(guest -> guest.getId().equals(guestId));

            if (removed) {
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

    private GuestDTO convertToGuestDTO(Guest guest) {
        return new GuestDTO(guest.getFirstName(), guest.getLastName(), guest.getAdditionalGuests(), guest.getComment(), guest.getCustomPrice());
    }
}