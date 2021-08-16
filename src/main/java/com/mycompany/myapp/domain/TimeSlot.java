package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.AvailAbilityStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TimeSlot.
 */
@Entity
@Table(name = "time_slot")
public class TimeSlot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    @NotNull
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @NotNull
    @Column(name = "remaining", nullable = false)
    private Integer remaining;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "availability_status", nullable = false)
    private AvailAbilityStatus availabilityStatus;

    @NotNull
    @Column(name = "center_name", nullable = false)
    private String centerName;

    @NotNull
    @Column(name = "action_by", nullable = false)
    private String actionBy;

    @NotNull
    @Column(name = "created_at", nullable = false, unique = true)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "timeSlot")
    @JsonIgnoreProperties(value = { "timeSlot" }, allowSetters = true)
    private Set<Reservation> reservations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "timeSlots" }, allowSetters = true)
    private GenerationRule generationRule;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeSlot id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public TimeSlot startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public TimeSlot endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Integer getCapacity() {
        return this.capacity;
    }

    public TimeSlot capacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getRemaining() {
        return this.remaining;
    }

    public TimeSlot remaining(Integer remaining) {
        this.remaining = remaining;
        return this;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    public AvailAbilityStatus getAvailabilityStatus() {
        return this.availabilityStatus;
    }

    public TimeSlot availabilityStatus(AvailAbilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
        return this;
    }

    public void setAvailabilityStatus(AvailAbilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public String getCenterName() {
        return this.centerName;
    }

    public TimeSlot centerName(String centerName) {
        this.centerName = centerName;
        return this;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getActionBy() {
        return this.actionBy;
    }

    public TimeSlot actionBy(String actionBy) {
        this.actionBy = actionBy;
        return this;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public TimeSlot createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public TimeSlot updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Reservation> getReservations() {
        return this.reservations;
    }

    public TimeSlot reservations(Set<Reservation> reservations) {
        this.setReservations(reservations);
        return this;
    }

    public TimeSlot addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setTimeSlot(this);
        return this;
    }

    public TimeSlot removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.setTimeSlot(null);
        return this;
    }

    public void setReservations(Set<Reservation> reservations) {
        if (this.reservations != null) {
            this.reservations.forEach(i -> i.setTimeSlot(null));
        }
        if (reservations != null) {
            reservations.forEach(i -> i.setTimeSlot(this));
        }
        this.reservations = reservations;
    }

    public GenerationRule getGenerationRule() {
        return this.generationRule;
    }

    public TimeSlot generationRule(GenerationRule generationRule) {
        this.setGenerationRule(generationRule);
        return this;
    }

    public void setGenerationRule(GenerationRule generationRule) {
        this.generationRule = generationRule;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeSlot)) {
            return false;
        }
        return id != null && id.equals(((TimeSlot) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimeSlot{" +
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
            "}";
    }
}
