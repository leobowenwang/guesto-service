package guesto.event.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

@Serdeable
@Introspected
public class GuestListDTO {
    private Long id;
    private Long eventId;

    private List<GuestDTO> guestList;

    public GuestListDTO(Long id, Long eventId, List<GuestDTO> guestList) {
        this.id = id;
        this.eventId = eventId;
        this.guestList = guestList;
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

    public GuestListDTO setGuestList(List<GuestDTO> guestList) {
        this.guestList = guestList;
        return this;
    }
    public List<GuestDTO> getGuestList() {
        return guestList;
    }

}
