package guesto.event.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@Introspected
public class GuestResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private int additionalGuests;
    private String comment;
    private int customPrice;
    private Long guestId;

    public GuestResponseDTO() {
    }

    public GuestResponseDTO(String firstName, String lastName, int additionalGuests, String comment, int customPrice, Long guestId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.additionalGuests = additionalGuests;
        this.comment = comment;
        this.customPrice = customPrice;
        this.guestId = guestId;
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

    public int getAdditionalGuests() {
        return additionalGuests;
    }

    public void setAdditionalGuests(int additionalGuests) {
        this.additionalGuests = additionalGuests;
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

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }
}
