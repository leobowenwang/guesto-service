package guesto.event.model;

import jakarta.persistence.*;

@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private boolean isCheckedIn;

    @ManyToOne
    @JoinColumn(name = "guest_list_id")
    private GuestList guestList;

    public Guest() {
    }

    public Guest(Long id, String firstName, String lastName, boolean isCheckedIn, GuestList guestList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isCheckedIn = isCheckedIn;
        this.guestList = guestList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isCheckedIn() {
        return isCheckedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        isCheckedIn = checkedIn;
    }

    public GuestList getGuestList() {
        return guestList;
    }

    public void setGuestList(GuestList guestList) {
        this.guestList = guestList;
    }
}