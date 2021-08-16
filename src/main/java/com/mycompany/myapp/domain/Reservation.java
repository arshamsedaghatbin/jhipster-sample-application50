package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.ReferenceType;
import com.mycompany.myapp.domain.enumeration.ReservationStatus;
import com.mycompany.myapp.domain.enumeration.ReservatorType;
import com.mycompany.myapp.domain.enumeration.ReserveType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Reservation.
 */
@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "reservator_id", nullable = false)
    private String reservatorId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "reservator_type", nullable = false)
    private ReservatorType reservatorType;

    @NotNull
    @Column(name = "reference_id", nullable = false)
    private String referenceId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type", nullable = false)
    private ReferenceType referenceType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "reserve_type", nullable = false)
    private ReserveType reserveType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @NotNull
    @Column(name = "action_by", nullable = false)
    private String actionBy;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne
    @JsonIgnoreProperties(value = { "reservations", "generationRule" }, allowSetters = true)
    private TimeSlot timeSlot;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reservation id(Long id) {
        this.id = id;
        return this;
    }

    public String getReservatorId() {
        return this.reservatorId;
    }

    public Reservation reservatorId(String reservatorId) {
        this.reservatorId = reservatorId;
        return this;
    }

    public void setReservatorId(String reservatorId) {
        this.reservatorId = reservatorId;
    }

    public ReservatorType getReservatorType() {
        return this.reservatorType;
    }

    public Reservation reservatorType(ReservatorType reservatorType) {
        this.reservatorType = reservatorType;
        return this;
    }

    public void setReservatorType(ReservatorType reservatorType) {
        this.reservatorType = reservatorType;
    }

    public String getReferenceId() {
        return this.referenceId;
    }

    public Reservation referenceId(String referenceId) {
        this.referenceId = referenceId;
        return this;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public ReferenceType getReferenceType() {
        return this.referenceType;
    }

    public Reservation referenceType(ReferenceType referenceType) {
        this.referenceType = referenceType;
        return this;
    }

    public void setReferenceType(ReferenceType referenceType) {
        this.referenceType = referenceType;
    }

    public ReserveType getReserveType() {
        return this.reserveType;
    }

    public Reservation reserveType(ReserveType reserveType) {
        this.reserveType = reserveType;
        return this;
    }

    public void setReserveType(ReserveType reserveType) {
        this.reserveType = reserveType;
    }

    public ReservationStatus getStatus() {
        return this.status;
    }

    public Reservation status(ReservationStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public String getActionBy() {
        return this.actionBy;
    }

    public Reservation actionBy(String actionBy) {
        this.actionBy = actionBy;
        return this;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Reservation createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Reservation updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public TimeSlot getTimeSlot() {
        return this.timeSlot;
    }

    public Reservation timeSlot(TimeSlot timeSlot) {
        this.setTimeSlot(timeSlot);
        return this;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservation)) {
            return false;
        }
        return id != null && id.equals(((Reservation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + getId() +
            ", reservatorId='" + getReservatorId() + "'" +
            ", reservatorType='" + getReservatorType() + "'" +
            ", referenceId='" + getReferenceId() + "'" +
            ", referenceType='" + getReferenceType() + "'" +
            ", reserveType='" + getReserveType() + "'" +
            ", status='" + getStatus() + "'" +
            ", actionBy='" + getActionBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
