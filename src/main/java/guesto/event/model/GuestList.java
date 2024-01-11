package guesto.event.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class GuestList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "guestList", cascade = CascadeType.ALL)
    private List<Guest> guestList;

    public GuestList() {
    }

    public GuestList(Event event) {
        this.event = event;
    }

    public GuestList(Long id, Event event, List<Guest> guestList) {
        this.id = id;
        this.event = event;
        this.guestList = guestList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Guest> getGuestList() {
        return guestList;
    }

    public void setGuests(List<Guest> guestList) {
        this.guestList = guestList;
    }
}
