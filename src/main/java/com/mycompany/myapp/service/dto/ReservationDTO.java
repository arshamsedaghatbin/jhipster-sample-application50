package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.ReferenceType;
import com.mycompany.myapp.domain.enumeration.ReservationStatus;
import com.mycompany.myapp.domain.enumeration.ReservatorType;
import com.mycompany.myapp.domain.enumeration.ReserveType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Reservation} entity.
 */
public class ReservationDTO implements Serializable {

    private Long id;

    @NotNull
    private String reservatorId;

    @NotNull
    private ReservatorType reservatorType;

    @NotNull
    private String referenceId;

    @NotNull
    private ReferenceType referenceType;

    @NotNull
    private ReserveType reserveType;

    @NotNull
    private ReservationStatus status;

    @NotNull
    private String actionBy;

    @NotNull
    private Instant createdAt;

    private Instant updatedAt;

    private TimeSlotDTO timeSlot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReservatorId() {
        return reservatorId;
    }

    public void setReservatorId(String reservatorId) {
        this.reservatorId = reservatorId;
    }

    public ReservatorType getReservatorType() {
        return reservatorType;
    }

    public void setReservatorType(ReservatorType reservatorType) {
        this.reservatorType = reservatorType;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public ReferenceType getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(ReferenceType referenceType) {
        this.referenceType = referenceType;
    }

    public ReserveType getReserveType() {
        return reserveType;
    }

    public void setReserveType(ReserveType reserveType) {
        this.reserveType = reserveType;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
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

    public TimeSlotDTO getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlotDTO timeSlot) {
        this.timeSlot = timeSlot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReservationDTO)) {
            return false;
        }

        ReservationDTO reservationDTO = (ReservationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reservationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReservationDTO{" +
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
            ", timeSlot=" + getTimeSlot() +
            "}";
    }
}
