package guesto.event.controller;

import guesto.event.dto.EventDTO;
import guesto.event.model.Event;
import guesto.event.service.EventService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;

import java.util.List;

@Controller("/events")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class EventController {

    private final EventService eventService;

    @Inject
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Post
    public HttpResponse<Event> createEvent(@Body EventDTO eventDTO) {
        Event createdEvent = eventService.createEvent(eventDTO);
        return HttpResponse.created(createdEvent);
    }

    @Get
    public List<Event> listEvents() {
        return eventService.listEvents();
    }

    @Put("/{id}")
    public HttpResponse<EventDTO> updateEvent(@PathVariable Long id, @Body EventDTO eventDTO, Authentication authentication) {
        String username = authentication.getName();
        try {
            EventDTO updatedEventDTO = eventService.updateEvent(id, eventDTO, username);
            return HttpResponse.ok(updatedEventDTO);
        } catch (RuntimeException e) {
            return HttpResponse.unauthorized();
        }
    }


    @Delete("/{id}")
    public HttpResponse<?> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return HttpResponse.noContent();
    }
}