package guesto.event;

import guesto.event.dto.EventDTO;
import guesto.event.dto.GuestDTO;
import guesto.event.dto.GuestResponseDTO;
import guesto.event.model.Event;
import guesto.event.service.EventService;
import guesto.event.service.GuestService;
import guesto.user.model.Role;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

import java.util.List;

@Controller("/event")
@Secured(SecurityRule.IS_AUTHENTICATED)
@Tag(name = "Events", description = "Operations related to events management")
public class EventController {

    private final EventService eventService;
    private final GuestService guestService;

    @Inject
    public EventController(EventService eventService, GuestService guestService) {
        this.eventService = eventService;
        this.guestService = guestService;
    }

    @Post
    @Secured(Role.ADMIN)
    @Operation(summary = "Create Event", description = "Creates a new event with the given details.")
    public HttpResponse<EventDTO> createEvent(@Body EventDTO eventDTO, Authentication authentication) {
        EventDTO createdEvent = eventService.createEvent(eventDTO, authentication);
        return HttpResponse.ok(createdEvent);
    }

    @Get
    @Operation(summary = "List Events", description = "Retrieves a list of all events.")
    public List<EventDTO> listEvents() {
        return eventService.listEvents();
    }

    @Put("/{eventId}")
    @Secured(Role.ADMIN)
    @Operation(summary = "Update Event", description = "Updates the event with the specified ID.")
    public HttpResponse<EventDTO> updateEvent(@PathVariable Long eventId, @Body EventDTO eventDTO) {
        EventDTO updatedEventDTO = eventService.updateEvent(eventId, eventDTO);
        return HttpResponse.ok(updatedEventDTO);
    }

    @Delete("/{eventId}")
    @Secured(Role.ADMIN)
    @Operation(summary = "Delete Event", description = "Deletes the event with the specified ID.")
    public HttpResponse<?> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return HttpResponse.noContent();
    }

    @Put("/{eventId}/check-in")
    @Operation(summary = "Check In Guest", description = "Checks in a guest for the event with the specified ID.")
    public HttpResponse<?> checkInGuest(@PathVariable Long eventId, @Body Long guestId) {
        boolean success = guestService.checkInGuest(eventId, guestId);
        if (success) {
            return HttpResponse.ok("Guest checked in successfully.");
        } else {
            return HttpResponse.badRequest("Unable to check in guest.");
        }
    }

    @Get("/{eventId}/guest")
    @Secured(Role.ADMIN)
    @Operation(summary = "List All Guests", description = "Lists all guests for the specified event.")
    public HttpResponse<List<GuestResponseDTO>> listAllGuest(@PathVariable Long eventId) {
        List<GuestResponseDTO> guests = guestService.listAllGuests(eventId);
        return HttpResponse.ok(guests);
    }


    @Post("/{eventId}/guest")
    @Secured({Role.ADMIN, Role.PROMOTER})
    @Operation(summary = "Add Guest to Event", description = "Adds a guest to the specified event's guest list.")
    public HttpResponse<GuestResponseDTO> addGuestToEvent(@PathVariable Long eventId, @Body GuestDTO guestDTO) {
        GuestResponseDTO addedGuestDTO = guestService.addGuestToEvent(eventId, guestDTO);
        return HttpResponse.ok(addedGuestDTO);
    }

    @Put("/{eventId}/guest/{guestId}")
    @Secured({Role.ADMIN, Role.PROMOTER})
    @Operation(summary = "Update Guest in Event", description = "Updates a guest in the specified event's guest list.")
    public HttpResponse<GuestResponseDTO> updateGuestInEvent(@PathVariable Long eventId, @PathVariable Long guestId, @Body GuestDTO updatedGuestDTO) {
        GuestResponseDTO updatedGuest = guestService.updateGuestInEvent(eventId, guestId, updatedGuestDTO);
        return HttpResponse.ok(updatedGuest);
    }

    @Delete("/{eventId}/guest/{guestId}")
    @Secured(Role.ADMIN)
    @Operation(summary = "Delete Guest from Event", description = "Deletes a guest from the specified event's guest list.")
    public HttpResponse<?> deleteGuestFromEvent(@PathVariable Long eventId, @PathVariable Long guestId) {
        boolean removed = guestService.deleteGuestFromEvent(eventId, guestId);
        if (removed) {
            return HttpResponse.noContent();
        } else {
            return HttpResponse.badRequest("Unable to delete guest.");
        }
    }
}
