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
import jakarta.inject.Inject;

import java.util.List;

@Controller("/event")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class EventController {

    private final EventService eventService;

    @Inject
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Post
    @Secured(Role.ADMIN)
    public HttpResponse<Event> createEvent(@Body EventDTO eventDTO, Authentication authentication) {
        Event createdEvent = eventService.createEvent(eventDTO, authentication);
        return HttpResponse.ok(createdEvent);
    }

    @Get
    public List<Event> listEvents() {
        return eventService.listEvents();
    }

    @Put("/{id}")
    @Secured(Role.ADMIN)
    public HttpResponse<EventDTO> updateEvent(@PathVariable Long id, @Body EventDTO eventDTO) {
        EventDTO updatedEventDTO = eventService.updateEvent(id, eventDTO);
        return HttpResponse.ok(updatedEventDTO);
    }

    @Delete("/{id}")
    @Secured(Role.ADMIN)
    public HttpResponse<?> deleteEvent(@PathVariable Long id, Authentication authentication) {
        eventService.deleteEvent(id);
        return HttpResponse.noContent();
    }

    @Put("/{id}/checkin")
    public HttpResponse<?> checkInGuest(@PathVariable Long id, @Body String guestName, Authentication authentication) {
        boolean success = eventService.checkInGuest(id, guestName);
        if (success) {
            return HttpResponse.ok("Guest checked in successfully.");
        } else {
            return HttpResponse.badRequest("Unable to check in guest.");
        }
    }
}