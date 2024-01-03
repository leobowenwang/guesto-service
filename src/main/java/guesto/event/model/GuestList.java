package guesto.event.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class GuestList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "guestList", cascade = CascadeType.ALL)
    private List<Guest> guests;

    public GuestList() {
    }

    public GuestList(Long id, Event event, List<Guest> guests) {
        this.id = id;
        this.event = event;
        this.guests = guests;
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

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }
}
