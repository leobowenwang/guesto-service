package guesto.event.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Serdeable
@Introspected
public class EventResponseDTO {

    private Long id;
    private String eventName;
    private LocalDateTime eventTime;
    private int maxGuestList;
    private BigInteger price;
    private String location;

    public EventResponseDTO() {
    }

    public EventResponseDTO(Long id, String eventName, LocalDateTime eventTime, int maxGuestList, BigInteger price, String location) {
        this.id = id;
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.maxGuestList = maxGuestList;
        this.price = price;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public int getMaxGuestList() {
        return maxGuestList;
    }

    public BigInteger getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public void setMaxGuestList(int maxGuestList) {
        this.maxGuestList = maxGuestList;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}