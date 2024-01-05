package guesto.event.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Serdeable
@Introspected
public class EventDTO {

    private String eventName;
    private LocalDateTime eventTime;
    private int maxGuestList;
    private BigInteger price;
    private String location;
    private GuestListDTO guestList;

    public EventDTO() {
    }

    public EventDTO(String eventName, LocalDateTime eventTime, int maxGuestList, BigInteger price, String location) {
        this(eventName, eventTime, maxGuestList, price, location, null);
    }

    public EventDTO(String eventName, LocalDateTime eventTime, int maxGuestList, BigInteger price, String location, GuestListDTO guestList) {
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.maxGuestList = maxGuestList;
        this.price = price;
        this.location = location;
        this.guestList = guestList;
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

    public GuestListDTO getGuestList() {
        return guestList;
    }

    public void setGuestList(GuestListDTO guestList) {
        this.guestList = guestList;
    }
}
