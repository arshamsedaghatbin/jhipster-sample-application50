package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.GenerationRuleStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.GenerationRule} entity.
 */
public class GenerationRuleDTO implements Serializable {

    private Long id;

    @NotNull
    private String centerName;

    @NotNull
    private Instant validFrom;

    @NotNull
    private String validTo;

    @NotNull
    private Integer daysOfWeek;

    @NotNull
    private Integer defaultCapacity;

    @NotNull
    private Instant startSlotsTime;

    @NotNull
    private Instant endSlotsTime;

    @NotNull
    private Integer slotDuration;

    @NotNull
    private Integer maxAvailableDays;

    @NotNull
    private GenerationRuleStatus status;

    @NotNull
    private String actionBy;

    @NotNull
    private Instant createdAt;

    private Instant updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public Integer getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(Integer daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public Integer getDefaultCapacity() {
        return defaultCapacity;
    }

    public void setDefaultCapacity(Integer defaultCapacity) {
        this.defaultCapacity = defaultCapacity;
    }

    public Instant getStartSlotsTime() {
        return startSlotsTime;
    }

    public void setStartSlotsTime(Instant startSlotsTime) {
        this.startSlotsTime = startSlotsTime;
    }

    public Instant getEndSlotsTime() {
        return endSlotsTime;
    }

    public void setEndSlotsTime(Instant endSlotsTime) {
        this.endSlotsTime = endSlotsTime;
    }

    public Integer getSlotDuration() {
        return slotDuration;
    }

    public void setSlotDuration(Integer slotDuration) {
        this.slotDuration = slotDuration;
    }

    public Integer getMaxAvailableDays() {
        return maxAvailableDays;
    }

    public void setMaxAvailableDays(Integer maxAvailableDays) {
        this.maxAvailableDays = maxAvailableDays;
    }

    public GenerationRuleStatus getStatus() {
        return status;
    }

    public void setStatus(GenerationRuleStatus status) {
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GenerationRuleDTO)) {
            return false;
        }

        GenerationRuleDTO generationRuleDTO = (GenerationRuleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, generationRuleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GenerationRuleDTO{" +
            "id=" + getId() +
            ", centerName='" + getCenterName() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", daysOfWeek=" + getDaysOfWeek() +
            ", defaultCapacity=" + getDefaultCapacity() +
            ", startSlotsTime='" + getStartSlotsTime() + "'" +
            ", endSlotsTime='" + getEndSlotsTime() + "'" +
            ", slotDuration=" + getSlotDuration() +
            ", maxAvailableDays=" + getMaxAvailableDays() +
            ", status='" + getStatus() + "'" +
            ", actionBy='" + getActionBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
