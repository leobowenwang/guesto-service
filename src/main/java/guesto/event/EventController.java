package guesto.event;

import guesto.event.dto.EventDTO;
import guesto.event.model.Event;
import guesto.event.service.EventService;
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

    @Inject
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Post
    @Secured(Role.ADMIN)
    @Operation(summary = "Create Event", description = "Creates a new event with the given details.")
    public HttpResponse<Event> createEvent(@Body EventDTO eventDTO, Authentication authentication) {
        Event createdEvent = eventService.createEvent(eventDTO, authentication);
        return HttpResponse.ok(createdEvent);
    }

    @Get
    @Operation(summary = "List Events", description = "Retrieves a list of all events.")
    public List<Event> listEvents() {
        return eventService.listEvents();
    }

    @Put("/{id}")
    @Secured(Role.ADMIN)
    @Operation(summary = "Update Event", description = "Updates the event with the specified ID.")
    public HttpResponse<EventDTO> updateEvent(@PathVariable Long id, @Body EventDTO eventDTO) {
        EventDTO updatedEventDTO = eventService.updateEvent(id, eventDTO);
        return HttpResponse.ok(updatedEventDTO);
    }

    @Delete("/{id}")
    @Secured(Role.ADMIN)
    @Operation(summary = "Delete Event", description = "Deletes the event with the specified ID.")
    public HttpResponse<?> deleteEvent(@PathVariable Long id, Authentication authentication) {
        eventService.deleteEvent(id);
        return HttpResponse.noContent();
    }

    @Put("/{id}/checkin")
    @Operation(summary = "Check In Guest", description = "Checks in a guest for the event with the specified ID.")
    public HttpResponse<?> checkInGuest(@PathVariable Long id, @Body String guestName, Authentication authentication) {
        boolean success = eventService.checkInGuest(id, guestName);
        if (success) {
            return HttpResponse.ok("Guest checked in successfully.");
        } else {
            return HttpResponse.badRequest("Unable to check in guest.");
        }
    }
}
