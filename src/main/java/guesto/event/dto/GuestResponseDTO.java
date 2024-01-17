package guesto.event.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.time.LocalDateTime;

@Serdeable
@Introspected
public class GuestResponseDTO {
    private Long id;
    private Long guestId;
    private String firstName;
    private String lastName;
    private int additionalGuests;
    private int remainingCheckIns;
    private boolean checkedIn;
    private int customPrice;
    private String comment;
    private Long addedBy;
    private LocalDateTime createdTime;

    public GuestResponseDTO() {
    }

    public GuestResponseDTO(Long id, Long guestId, String firstName, String lastName, int additionalGuests, int remainingCheckIns, boolean checkedIn, int customPrice, String comment, Long addedBy, LocalDateTime createdTime) {
        this.id = id;
        this.guestId = guestId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.additionalGuests = additionalGuests;
        this.remainingCheckIns = remainingCheckIns;
        this.checkedIn = checkedIn;
        this.customPrice = customPrice;
        this.comment = comment;
        this.addedBy = addedBy;
        this.createdTime = createdTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
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

    public int getRemainingCheckIns() {
        return remainingCheckIns;
    }

    public void setRemainingCheckIns(int remainingCheckIns) {
        this.remainingCheckIns = remainingCheckIns;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public int getCustomPrice() {
        return customPrice;
    }

    public void setCustomPrice(int customPrice) {
        this.customPrice = customPrice;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Long addedBy) {
        this.addedBy = addedBy;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
