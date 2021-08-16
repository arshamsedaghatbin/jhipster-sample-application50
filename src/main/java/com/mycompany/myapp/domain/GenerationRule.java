package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.GenerationRuleStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A GenerationRule.
 */
@Entity
@Table(name = "generation_rule")
public class GenerationRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "center_name", nullable = false, unique = true)
    private String centerName;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @NotNull
    @Column(name = "valid_to", nullable = false)
    private String validTo;

    @NotNull
    @Column(name = "days_of_week", nullable = false)
    private Integer daysOfWeek;

    @NotNull
    @Column(name = "default_capacity", nullable = false)
    private Integer defaultCapacity;

    @NotNull
    @Column(name = "start_slots_time", nullable = false)
    private Instant startSlotsTime;

    @NotNull
    @Column(name = "end_slots_time", nullable = false)
    private Instant endSlotsTime;

    @NotNull
    @Column(name = "slot_duration", nullable = false)
    private Integer slotDuration;

    @NotNull
    @Column(name = "max_available_days", nullable = false)
    private Integer maxAvailableDays;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GenerationRuleStatus status;

    @NotNull
    @Column(name = "action_by", nullable = false)
    private String actionBy;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "generationRule")
    @JsonIgnoreProperties(value = { "reservations", "generationRule" }, allowSetters = true)
    private Set<TimeSlot> timeSlots = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GenerationRule id(Long id) {
        this.id = id;
        return this;
    }

    public String getCenterName() {
        return this.centerName;
    }

    public GenerationRule centerName(String centerName) {
        this.centerName = centerName;
        return this;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public Instant getValidFrom() {
        return this.validFrom;
    }

    public GenerationRule validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return this.validTo;
    }

    public GenerationRule validTo(String validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public Integer getDaysOfWeek() {
        return this.daysOfWeek;
    }

    public GenerationRule daysOfWeek(Integer daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
        return this;
    }

    public void setDaysOfWeek(Integer daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public Integer getDefaultCapacity() {
        return this.defaultCapacity;
    }

    public GenerationRule defaultCapacity(Integer defaultCapacity) {
        this.defaultCapacity = defaultCapacity;
        return this;
    }

    public void setDefaultCapacity(Integer defaultCapacity) {
        this.defaultCapacity = defaultCapacity;
    }

    public Instant getStartSlotsTime() {
        return this.startSlotsTime;
    }

    public GenerationRule startSlotsTime(Instant startSlotsTime) {
        this.startSlotsTime = startSlotsTime;
        return this;
    }

    public void setStartSlotsTime(Instant startSlotsTime) {
        this.startSlotsTime = startSlotsTime;
    }

    public Instant getEndSlotsTime() {
        return this.endSlotsTime;
    }

    public GenerationRule endSlotsTime(Instant endSlotsTime) {
        this.endSlotsTime = endSlotsTime;
        return this;
    }

    public void setEndSlotsTime(Instant endSlotsTime) {
        this.endSlotsTime = endSlotsTime;
    }

    public Integer getSlotDuration() {
        return this.slotDuration;
    }

    public GenerationRule slotDuration(Integer slotDuration) {
        this.slotDuration = slotDuration;
        return this;
    }

    public void setSlotDuration(Integer slotDuration) {
        this.slotDuration = slotDuration;
    }

    public Integer getMaxAvailableDays() {
        return this.maxAvailableDays;
    }

    public GenerationRule maxAvailableDays(Integer maxAvailableDays) {
        this.maxAvailableDays = maxAvailableDays;
        return this;
    }

    public void setMaxAvailableDays(Integer maxAvailableDays) {
        this.maxAvailableDays = maxAvailableDays;
    }

    public GenerationRuleStatus getStatus() {
        return this.status;
    }

    public GenerationRule status(GenerationRuleStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(GenerationRuleStatus status) {
        this.status = status;
    }

    public String getActionBy() {
        return this.actionBy;
    }

    public GenerationRule actionBy(String actionBy) {
        this.actionBy = actionBy;
        return this;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public GenerationRule createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public GenerationRule updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<TimeSlot> getTimeSlots() {
        return this.timeSlots;
    }

    public GenerationRule timeSlots(Set<TimeSlot> timeSlots) {
        this.setTimeSlots(timeSlots);
        return this;
    }

    public GenerationRule addTimeSlot(TimeSlot timeSlot) {
        this.timeSlots.add(timeSlot);
        timeSlot.setGenerationRule(this);
        return this;
    }

    public GenerationRule removeTimeSlot(TimeSlot timeSlot) {
        this.timeSlots.remove(timeSlot);
        timeSlot.setGenerationRule(null);
        return this;
    }

    public void setTimeSlots(Set<TimeSlot> timeSlots) {
        if (this.timeSlots != null) {
            this.timeSlots.forEach(i -> i.setGenerationRule(null));
        }
        if (timeSlots != null) {
            timeSlots.forEach(i -> i.setGenerationRule(this));
        }
        this.timeSlots = timeSlots;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GenerationRule)) {
            return false;
        }
        return id != null && id.equals(((GenerationRule) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GenerationRule{" +
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
