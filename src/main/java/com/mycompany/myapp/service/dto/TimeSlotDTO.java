package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.AvailAbilityStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.TimeSlot} entity.
 */
public class TimeSlotDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant startTime;

    @NotNull
    private Instant endTime;

    @NotNull
    private Integer capacity;

    @NotNull
    private Integer remaining;

    @NotNull
    private AvailAbilityStatus availabilityStatus;

    @NotNull
    private String centerName;

    @NotNull
    private String actionBy;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;

    private GenerationRuleDTO generationRule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    public AvailAbilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(AvailAbilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public GenerationRuleDTO getGenerationRule() {
        return generationRule;
    }

    public void setGenerationRule(GenerationRuleDTO generationRule) {
        this.generationRule = generationRule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeSlotDTO)) {
            return false;
        }

        TimeSlotDTO timeSlotDTO = (TimeSlotDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, timeSlotDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimeSlotDTO{" +
            "id=" + getId() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", capacity=" + getCapacity() +
            ", remaining=" + getRemaining() +
            ", availabilityStatus='" + getAvailabilityStatus() + "'" +
            ", centerName='" + getCenterName() + "'" +
            ", actionBy='" + getActionBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", generationRule=" + getGenerationRule() +
            "}";
    }
}
