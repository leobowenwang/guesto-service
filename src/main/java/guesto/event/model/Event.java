package guesto.event.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Introspected
@Serdeable
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String eventName;
    private LocalDateTime eventTime;
    private int maxGuestList;
    private BigInteger price;
    private String location;
    private String createdBy;
    private LocalDateTime createdTime;
    private int checkedInGuestsCount;
    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL)
    private GuestList guestList;

    public Event() {
    }

    public Event(String eventName, LocalDateTime eventTime, int maxGuestList, BigInteger price, String location, String createdBy, LocalDateTime createdTime) {
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.maxGuestList = maxGuestList;
        this.price = price;
        this.location = location;
        this.createdBy = createdBy;
        this.createdTime = createdTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public int getMaxGuestList() {
        return maxGuestList;
    }

    public void setMaxGuestList(int maxGuestList) {
        this.maxGuestList = maxGuestList;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public int getCheckedInGuestsCount() {
        return checkedInGuestsCount;
    }

    public void setCheckedInGuestsCount(int checkedInGuestsCount) {
        this.checkedInGuestsCount = checkedInGuestsCount;
    }

    public void incrementCheckedInGuests() {
        this.checkedInGuestsCount++;
    }

    public GuestList getGuestList() {
        return guestList;
    }

    public void setGuestList(GuestList guestList) {
        this.guestList = guestList;
    }
}
