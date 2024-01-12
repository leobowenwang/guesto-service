package guesto.event.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@Introspected
public class GuestDTO {
    private String firstName;
    private String lastName;
    private int additionalGuests;
    private String comment;
    private int customPrice;

    public GuestDTO(String firstName, String lastName, int additionalGuests, String comment, int customPrice) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.additionalGuests = additionalGuests;
        this.comment = comment;
        this.customPrice = customPrice;
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

    public int getAdditionalGuests() {
        return additionalGuests;
    }

    public GuestDTO setAdditionalGuests(int additionalGuests) {
        this.additionalGuests = additionalGuests;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public GuestDTO setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public int getCustomPrice() {
        return customPrice;
    }

    public GuestDTO setCustomPrice(int customPrice) {
        this.customPrice = customPrice;
        return this;
    }
}
