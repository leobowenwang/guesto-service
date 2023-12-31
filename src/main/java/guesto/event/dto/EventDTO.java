package guesto.event.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Serdeable
@Introspected
public class EventDTO {

    private String name;
    private LocalDateTime eventDate;
    private int maxGuestList;
    private BigInteger price;
    private String location;

    public EventDTO() {
    }

    public EventDTO(String name, LocalDateTime eventDate, int maxGuestList, BigInteger price, String location) {
        this.name = name;
        this.eventDate = eventDate;
        this.maxGuestList = maxGuestList;
        this.price = price;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
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
