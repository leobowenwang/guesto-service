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
    private int additionalGuests;
    private int remainingCheckIns;
    private String comment;
    private int customPrice;

    @ManyToOne
    @JoinColumn(name = "guest_list_id")
    private GuestList guestList;

    public Guest() {
    }

    public Guest(String firstName, String lastName, boolean isCheckedIn, GuestList guestList,
                 int additionalGuests, String comment, int customPrice, int remainingCheckIns) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isCheckedIn = isCheckedIn;
        this.guestList = guestList;
        this.additionalGuests = additionalGuests;
        this.comment = comment;
        this.customPrice = customPrice;
        this.remainingCheckIns = remainingCheckIns;
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
        this.isCheckedIn = checkedIn;
    }

    public int getAdditionalGuests() {
        return additionalGuests;
    }

    public void setAdditionalGuests(int additionalGuests) {
        this.additionalGuests = additionalGuests;
    }

    public int getRemainingCheckIns() {
        return remainingCheckIns;
    }

    public void setRemainingCheckIns(int remainingCheckIns) {
        this.remainingCheckIns = remainingCheckIns;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCustomPrice() {
        return customPrice;
    }

    public void setCustomPrice(int customPrice) {
        this.customPrice = customPrice;
    }

    public GuestList getGuestList() {
        return guestList;
    }

    public void setGuestList(GuestList guestList) {
        this.guestList = guestList;
    }
}
