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

@Controller("/event")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class EventController {

    private final EventService eventService;

    @Inject
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Post
    @Secured("ADMIN")
    public HttpResponse<Event> createEvent(@Body EventDTO eventDTO) {
        Event createdEvent = eventService.createEvent(eventDTO);
        return HttpResponse.ok(createdEvent);
    }

    @Get
    public List<Event> listEvents() {
        return eventService.listEvents();
    }

    @Put("/{id}")
    @Secured("ADMIN")
    public HttpResponse<EventDTO> updateEvent(@PathVariable Long id, @Body EventDTO eventDTO) {
        EventDTO updatedEventDTO = eventService.updateEvent(id, eventDTO);
        return HttpResponse.ok(updatedEventDTO);
    }


    @Delete("/{id}")
    @Secured("ADMIN")
    public HttpResponse<?> deleteEvent(@PathVariable Long id, Authentication authentication) {
        eventService.deleteEvent(id);
        return HttpResponse.noContent();
    }
}