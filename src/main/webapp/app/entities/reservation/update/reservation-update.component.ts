import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IReservation, Reservation } from '../reservation.model';
import { ReservationService } from '../service/reservation.service';
import { ITimeSlot } from 'app/entities/time-slot/time-slot.model';
import { TimeSlotService } from 'app/entities/time-slot/service/time-slot.service';

@Component({
  selector: 'jhi-reservation-update',
  templateUrl: './reservation-update.component.html',
})
export class ReservationUpdateComponent implements OnInit {
  isSaving = false;

  timeSlotsSharedCollection: ITimeSlot[] = [];

  editForm = this.fb.group({
    id: [],
    reservatorId: [null, [Validators.required]],
    reservatorType: [null, [Validators.required]],
    referenceId: [null, [Validators.required]],
    referenceType: [null, [Validators.required]],
    reserveType: [null, [Validators.required]],
    status: [null, [Validators.required]],
    actionBy: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    updatedAt: [],
    timeSlot: [],
  });

  constructor(
    protected reservationService: ReservationService,
    protected timeSlotService: TimeSlotService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reservation }) => {
      if (reservation.id === undefined) {
        const today = dayjs().startOf('day');
        reservation.createdAt = today;
        reservation.updatedAt = today;
      }

      this.updateForm(reservation);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reservation = this.createFromForm();
    if (reservation.id !== undefined) {
      this.subscribeToSaveResponse(this.reservationService.update(reservation));
    } else {
      this.subscribeToSaveResponse(this.reservationService.create(reservation));
    }
  }

  trackTimeSlotById(index: number, item: ITimeSlot): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReservation>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(reservation: IReservation): void {
    this.editForm.patchValue({
      id: reservation.id,
      reservatorId: reservation.reservatorId,
      reservatorType: reservation.reservatorType,
      referenceId: reservation.referenceId,
      referenceType: reservation.referenceType,
      reserveType: reservation.reserveType,
      status: reservation.status,
      actionBy: reservation.actionBy,
      createdAt: reservation.createdAt ? reservation.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: reservation.updatedAt ? reservation.updatedAt.format(DATE_TIME_FORMAT) : null,
      timeSlot: reservation.timeSlot,
    });

    this.timeSlotsSharedCollection = this.timeSlotService.addTimeSlotToCollectionIfMissing(
      this.timeSlotsSharedCollection,
      reservation.timeSlot
    );
  }

  protected loadRelationshipsOptions(): void {
    this.timeSlotService
      .query()
      .pipe(map((res: HttpResponse<ITimeSlot[]>) => res.body ?? []))
      .pipe(
        map((timeSlots: ITimeSlot[]) =>
          this.timeSlotService.addTimeSlotToCollectionIfMissing(timeSlots, this.editForm.get('timeSlot')!.value)
        )
      )
      .subscribe((timeSlots: ITimeSlot[]) => (this.timeSlotsSharedCollection = timeSlots));
  }

  protected createFromForm(): IReservation {
    return {
      ...new Reservation(),
      id: this.editForm.get(['id'])!.value,
      reservatorId: this.editForm.get(['reservatorId'])!.value,
      reservatorType: this.editForm.get(['reservatorType'])!.value,
      referenceId: this.editForm.get(['referenceId'])!.value,
      referenceType: this.editForm.get(['referenceType'])!.value,
      reserveType: this.editForm.get(['reserveType'])!.value,
      status: this.editForm.get(['status'])!.value,
      actionBy: this.editForm.get(['actionBy'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? dayjs(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? dayjs(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      timeSlot: this.editForm.get(['timeSlot'])!.value,
    };
  }
}
