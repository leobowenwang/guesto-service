package guesto.event.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

@Serdeable
@Introspected
public class GuestDTO {
    private List<GuestDTO> guests;
    private String firstName;
    private String lastName;
    private boolean isCheckedIn;
    private int additionalGuests;

    public GuestDTO(String firstName, String lastName, boolean isCheckedIn, int additionalGuests) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isCheckedIn = isCheckedIn;
        this.additionalGuests = additionalGuests;
    }

    public String firstName() {
        return firstName;
    }

    public GuestDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String lastName() {
        return lastName;
    }

    public GuestDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public boolean isCheckedIn() {
        return isCheckedIn;
    }

    public GuestDTO setCheckedIn(boolean checkedIn) {
        isCheckedIn = checkedIn;
        return this;
    }

    public int getAdditionalGuests() {
        return additionalGuests;
    }

    public GuestDTO setAdditionalGuests(int additionalGuests) {
        this.additionalGuests = additionalGuests;
        return this;
    }

    public List<GuestDTO> getGuests() {
        return guests;
    }

    public void setGuests(List<GuestDTO> guests) {
        this.guests = guests;
    }

}
