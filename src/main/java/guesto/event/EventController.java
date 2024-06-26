package guesto.event;

import guesto.event.dto.EventDTO;
import guesto.event.dto.EventResponseDTO;
import guesto.event.dto.GuestDTO;
import guesto.event.dto.GuestResponseDTO;
import guesto.event.service.EventService;
import guesto.event.service.GuestService;
import guesto.user.dto.UserAssigmentDTO;
import guesto.user.model.Role;
import io.micronaut.core.annotation.Nullable;
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
    public HttpResponse<EventResponseDTO> createEvent(@Body EventDTO eventDTO, Authentication authentication) {
        EventResponseDTO createdEvent = eventService.createEvent(eventDTO, authentication);
        return HttpResponse.ok(createdEvent);
    }

    @Get("/{?sortBy,order}")
    @Operation(summary = "List Events", description = "Retrieves a list of all events.")
    public List<EventResponseDTO> listEvents(@Nullable @QueryValue String sortBy, @Nullable @QueryValue String order) {
        return eventService.listEventsSorted(sortBy, "asc".equalsIgnoreCase(order));
    }


    @Get("/{eventId}")
    @Operation(summary = "Get Event Detail", description = "Retrieves the details of a specific event.")
    public HttpResponse<EventResponseDTO> getEventDetail(@PathVariable Long eventId) {
        return eventService.getEventById(eventId).map(HttpResponse::ok).orElse(HttpResponse.notFound());
    }

    @Post("/{eventId}/assign")
    public HttpResponse<?> assignUserToEvent(Long eventId, @Body UserAssigmentDTO userAssignment) {
        eventService.assignUserToEvent(userAssignment.getUserId(), eventId);
        return HttpResponse.ok();
    }

    @Post("/{eventId}/unassign")
    public HttpResponse<?> unassignUserFromEvent(Long eventId, @Body UserAssigmentDTO userAssignment) {
        eventService.unassignUserFromEvent(userAssignment.getUserId(), eventId);
        return HttpResponse.ok();
    }

    @Put("/{eventId}")
    @Secured(Role.ADMIN)
    @Operation(summary = "Update Event", description = "Updates the event with the specified ID.")
    public HttpResponse<EventResponseDTO> updateEvent(@PathVariable Long eventId, @Body EventDTO eventDTO) {
        EventResponseDTO updatedEventDTO = eventService.updateEvent(eventId, eventDTO);
        return HttpResponse.ok(updatedEventDTO);
    }

    @Delete("/{eventId}")
    @Secured(Role.ADMIN)
    @Operation(summary = "Delete Event", description = "Deletes the event with the specified ID.")
    public HttpResponse<?> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return HttpResponse.noContent();
    }

    @Put("/{eventId}/check-in/{guestId}")
    @Operation(summary = "Check In Guest", description = "Checks in a guest for the event with the specified ID.")
    public HttpResponse<?> checkInGuest(@PathVariable Long eventId, @PathVariable Long guestId) {
        GuestResponseDTO responseDTO = guestService.checkInGuest(eventId, guestId);

        return HttpResponse.ok(responseDTO);
    }

    @Get("/{eventId}/guest{?sortBy,order}")
    @Secured({Role.ADMIN, Role.STAFF, Role.CONTROLLER})
    @Operation(summary = "List All Guests", description = "Lists all guests for the specified event.")
    public HttpResponse<List<GuestResponseDTO>> listAllGuest(@PathVariable Long eventId, @Nullable @QueryValue String sortBy, @Nullable @QueryValue String order) {
        List<GuestResponseDTO> guests = guestService.listAllGuests(eventId, sortBy, "asc".equalsIgnoreCase(order));
        return HttpResponse.ok(guests);
    }

    @Post("/{eventId}/guest")
    @Secured({Role.ADMIN, Role.STAFF})
    @Operation(summary = "Add Guest to Event", description = "Adds a guest to the specified event's guest list.")
    public HttpResponse<GuestResponseDTO> addGuestToEvent(@PathVariable Long eventId, @Body GuestDTO guestDTO, Authentication authentication) {
        GuestResponseDTO addedGuestDTO = guestService.addGuestToEvent(eventId, guestDTO, authentication);
        return HttpResponse.ok(addedGuestDTO);
    }

    @Put("/{eventId}/guest/{guestId}")
    @Secured({Role.ADMIN, Role.STAFF})
    @Operation(summary = "Update Guest in Event", description = "Updates a guest in the specified event's guest list.")
    public HttpResponse<GuestResponseDTO> updateGuestInEvent(@PathVariable Long eventId, @PathVariable Long guestId, @Body GuestDTO updatedGuestDTO, Authentication authentication) {
        GuestResponseDTO updatedGuest = guestService.updateGuestInEvent(eventId, guestId, updatedGuestDTO, authentication);
        return HttpResponse.ok(updatedGuest);
    }

    @Delete("/{eventId}/guest/{guestId}")
    @Secured({Role.ADMIN, Role.STAFF})
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
