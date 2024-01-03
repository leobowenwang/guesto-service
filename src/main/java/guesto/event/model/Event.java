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

    private String name;
    private LocalDateTime date;
    private int maxGuestList;
    private BigInteger price;
    private String location;

    public Event() {
    }

    public Event(String name, LocalDateTime date, int maxGuestList, BigInteger price, String location) {
        this.name = name;
        this.date = date;
        this.maxGuestList = maxGuestList;
        this.price = price;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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
}