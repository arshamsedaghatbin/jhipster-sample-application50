import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ITimeSlot, TimeSlot } from '../time-slot.model';
import { TimeSlotService } from '../service/time-slot.service';
import { IGenerationRule } from 'app/entities/generation-rule/generation-rule.model';
import { GenerationRuleService } from 'app/entities/generation-rule/service/generation-rule.service';

@Component({
  selector: 'jhi-time-slot-update',
  templateUrl: './time-slot-update.component.html',
})
export class TimeSlotUpdateComponent implements OnInit {
  isSaving = false;

  generationRulesSharedCollection: IGenerationRule[] = [];

  editForm = this.fb.group({
    id: [],
    startTime: [null, [Validators.required]],
    endTime: [null, [Validators.required]],
    capacity: [null, [Validators.required]],
    remaining: [null, [Validators.required]],
    availabilityStatus: [null, [Validators.required]],
    centerName: [null, [Validators.required]],
    actionBy: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    generationRule: [],
  });

  constructor(
    protected timeSlotService: TimeSlotService,
    protected generationRuleService: GenerationRuleService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ timeSlot }) => {
      if (timeSlot.id === undefined) {
        const today = dayjs().startOf('day');
        timeSlot.startTime = today;
        timeSlot.endTime = today;
        timeSlot.createdAt = today;
        timeSlot.updatedAt = today;
      }

      this.updateForm(timeSlot);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const timeSlot = this.createFromForm();
    if (timeSlot.id !== undefined) {
      this.subscribeToSaveResponse(this.timeSlotService.update(timeSlot));
    } else {
      this.subscribeToSaveResponse(this.timeSlotService.create(timeSlot));
    }
  }

  trackGenerationRuleById(index: number, item: IGenerationRule): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITimeSlot>>): void {
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

  protected updateForm(timeSlot: ITimeSlot): void {
    this.editForm.patchValue({
      id: timeSlot.id,
      startTime: timeSlot.startTime ? timeSlot.startTime.format(DATE_TIME_FORMAT) : null,
      endTime: timeSlot.endTime ? timeSlot.endTime.format(DATE_TIME_FORMAT) : null,
      capacity: timeSlot.capacity,
      remaining: timeSlot.remaining,
      availabilityStatus: timeSlot.availabilityStatus,
      centerName: timeSlot.centerName,
      actionBy: timeSlot.actionBy,
      createdAt: timeSlot.createdAt ? timeSlot.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: timeSlot.updatedAt ? timeSlot.updatedAt.format(DATE_TIME_FORMAT) : null,
      generationRule: timeSlot.generationRule,
    });

    this.generationRulesSharedCollection = this.generationRuleService.addGenerationRuleToCollectionIfMissing(
      this.generationRulesSharedCollection,
      timeSlot.generationRule
    );
  }

  protected loadRelationshipsOptions(): void {
    this.generationRuleService
      .query()
      .pipe(map((res: HttpResponse<IGenerationRule[]>) => res.body ?? []))
      .pipe(
        map((generationRules: IGenerationRule[]) =>
          this.generationRuleService.addGenerationRuleToCollectionIfMissing(generationRules, this.editForm.get('generationRule')!.value)
        )
      )
      .subscribe((generationRules: IGenerationRule[]) => (this.generationRulesSharedCollection = generationRules));
  }

  protected createFromForm(): ITimeSlot {
    return {
      ...new TimeSlot(),
      id: this.editForm.get(['id'])!.value,
      startTime: this.editForm.get(['startTime'])!.value ? dayjs(this.editForm.get(['startTime'])!.value, DATE_TIME_FORMAT) : undefined,
      endTime: this.editForm.get(['endTime'])!.value ? dayjs(this.editForm.get(['endTime'])!.value, DATE_TIME_FORMAT) : undefined,
      capacity: this.editForm.get(['capacity'])!.value,
      remaining: this.editForm.get(['remaining'])!.value,
      availabilityStatus: this.editForm.get(['availabilityStatus'])!.value,
      centerName: this.editForm.get(['centerName'])!.value,
      actionBy: this.editForm.get(['actionBy'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? dayjs(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? dayjs(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      generationRule: this.editForm.get(['generationRule'])!.value,
    };
  }
}
