package guesto.event.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

@Serdeable
@Introspected
public class GuestListDTO {
    private Long id;
    private Long eventId;
    private List<GuestDTO> guests;

    public GuestListDTO(Long id, Long eventId, List<GuestDTO> guests) {
        this.id = id;
        this.eventId = eventId;
        this.guests = guests;
    }

    public Long id() {
        return id;
    }

    public GuestListDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public Long eventId() {
        return eventId;
    }

    public GuestListDTO setEventId(Long eventId) {
        this.eventId = eventId;
        return this;
    }

    public List<GuestDTO> guests() {
        return guests;
    }

    public GuestListDTO setGuests(List<GuestDTO> guests) {
        this.guests = guests;
        return this;
    }


}
