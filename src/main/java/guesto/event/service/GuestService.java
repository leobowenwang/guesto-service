package guesto.event.service;

import guesto.event.dto.GuestDTO;
import guesto.event.dto.GuestResponseDTO;
import guesto.event.exception.EventNotFoundException;
import guesto.event.exception.GuestNotFoundException;
import guesto.event.model.Event;
import guesto.event.model.Guest;
import guesto.event.model.GuestList;
import guesto.event.repository.EventRepository;
import guesto.event.repository.GuestListRepository;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class GuestService {

    private final EventRepository eventRepository;
    private final GuestListRepository guestListRepository;

    @Inject
    public GuestService(EventRepository eventRepository, GuestListRepository guestListRepository) {
        this.eventRepository = eventRepository;
        this.guestListRepository = guestListRepository;
    }

    @Transactional
    public GuestResponseDTO checkInGuest(Long eventId, Long guestId) {
        return eventRepository.findById(eventId).map(event -> {
            GuestList guestList = guestListRepository.findByEventId(event.getId()).orElse(new GuestList(event));
            Optional<Guest> guestToCheckIn = guestList.getGuestList().stream().filter(guest -> guest.getId().equals(guestId)).findFirst();

            if (guestToCheckIn.isPresent()) {
                Guest guest = guestToCheckIn.get();

                // Check if guest is already checked in but remaining check-ins haven't been reset yet
                if (guest.isCheckedIn() && guest.getRemainingCheckIns() == 0) {
                    guest.setRemainingCheckIns(guest.getAdditionalGuests() + 1);
                    guest.setCheckedIn(false);
                } else if (guest.getRemainingCheckIns() > 0) {
                    guest.setRemainingCheckIns(guest.getRemainingCheckIns() - 1);
                    guest.setCheckedIn(guest.getRemainingCheckIns() == 0);
                }

                guestListRepository.update(guestList);
                eventRepository.update(event);
                return convertToGuestResponseDTO(guest);
            }
            throw new GuestNotFoundException("Guest not found");
        }).orElseThrow(() -> new EventNotFoundException("Event not found"));
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
            return convertToGuestResponseDTO(guest);
        }).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
    }

    public List<GuestResponseDTO> listAllGuests(Long eventId, String sortField, boolean isAscending) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
        List<Guest> guests = event.getGuestList().getGuestList();

        Stream<Guest> guestStream = guests.stream();

        if (sortField != null && !sortField.isEmpty()) {
            Comparator<Guest> comparator = switch (sortField.toLowerCase()) {
                case "id" -> Comparator.comparing(Guest::getId);
                case "firstname" -> Comparator.comparing(Guest::getFirstName);
                case "lastname" -> Comparator.comparing(Guest::getLastName);
                case "additionalguests" -> Comparator.comparing(Guest::getAdditionalGuests);
                case "remainingcheckins" -> Comparator.comparing(Guest::getRemainingCheckIns);
                case "checkedin" -> Comparator.comparing(Guest::isCheckedIn);
                case "comment" -> Comparator.comparing(Guest::getComment);
                case "customprice" -> Comparator.comparing(Guest::getCustomPrice);
                default -> null;
            };

            if (comparator != null) {
                guestStream = isAscending ? guestStream.sorted(comparator) : guestStream.sorted(comparator.reversed());
            }
        }

        return guestStream.map(this::convertToGuestResponseDTO).collect(Collectors.toList());
    }


    public GuestResponseDTO updateGuestInEvent(Long eventId, Long guestId, GuestDTO updatedGuestDTO) {
        return eventRepository.findById(eventId).map(event -> {
            GuestList guestList = guestListRepository.findByEventId(event.getId()).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));

            Optional<Guest> guestToUpdate = guestList.getGuestList().stream().filter(guest -> guest.getId().equals(guestId)).findFirst();

            if (guestToUpdate.isPresent()) {
                updateGuestFromDTO(guestToUpdate.get(), updatedGuestDTO);
                guestListRepository.update(guestList);
                eventRepository.update(event);
                return convertToGuestResponseDTO(guestToUpdate.get());
            } else {
                throw new GuestNotFoundException("Guest not found with ID: " + guestId);
            }
        }).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
    }


    public boolean deleteGuestFromEvent(Long eventId, Long guestId) {
        return eventRepository.findById(eventId).map(event -> {
            GuestList guestList = guestListRepository.findByEventId(event.getId())
                    .orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));

            boolean removed = guestList.getGuestList().removeIf(guest -> guest.getId().equals(guestId));
            if (removed) {
                guestListRepository.update(guestList);
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
        return new Guest(guestDTO.firstName(), guestDTO.lastName(), false, null, guestDTO.getAdditionalGuests(), guestDTO.getComment(), guestDTO.getCustomPrice(), guestDTO.getAdditionalGuests() + 1);
    }

    private GuestResponseDTO convertToGuestResponseDTO(Guest guest) {
        GuestResponseDTO dto = new GuestResponseDTO();
        dto.setId(guest.getId());
        dto.setFirstName(guest.getFirstName());
        dto.setLastName(guest.getLastName());
        dto.setAdditionalGuests(guest.getAdditionalGuests());
        dto.setComment(guest.getComment());
        dto.setCustomPrice(guest.getCustomPrice());
        dto.setRemainingCheckIns(guest.getRemainingCheckIns());
        dto.setCheckedIn(guest.isCheckedIn());
        return dto;
    }
}