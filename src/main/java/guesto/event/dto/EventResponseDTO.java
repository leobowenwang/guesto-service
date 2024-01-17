package guesto.event.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Serdeable
@Introspected
public class EventResponseDTO {

    private Long id;
    private String eventName;
    private LocalDateTime eventTime;
    private int maxGuestList;
    private BigInteger price;
    private String location;
    private Long createdBy;
    private LocalDateTime createdTime;
    private int checkedInGuestsCount;
    private int totalGuestCount;
    private List<Long> assignedUserIds;
    public EventResponseDTO() {
    }

    public EventResponseDTO(Long id, String eventName, LocalDateTime eventTime, int maxGuestList, BigInteger price, String location, Long createdBy, LocalDateTime createdTime) {
        this.id = id;
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

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
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

    public int getTotalGuestCount() {
        return totalGuestCount;
    }

    public void setTotalGuestCount(int totalGuestCount) {
        this.totalGuestCount = totalGuestCount;
    }

    public List<Long> getAssignedUserIds() {
        return assignedUserIds;
    }

    public void setAssignedUserIds(List<Long> assignedUserIds) {
        this.assignedUserIds = assignedUserIds;
    }
}
