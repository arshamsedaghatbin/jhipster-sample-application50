import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IGenerationRule, GenerationRule } from '../generation-rule.model';
import { GenerationRuleService } from '../service/generation-rule.service';

@Component({
  selector: 'jhi-generation-rule-update',
  templateUrl: './generation-rule-update.component.html',
})
export class GenerationRuleUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    centerName: [null, [Validators.required]],
    validFrom: [null, [Validators.required]],
    validTo: [null, [Validators.required]],
    daysOfWeek: [null, [Validators.required]],
    defaultCapacity: [null, [Validators.required]],
    startSlotsTime: [null, [Validators.required]],
    endSlotsTime: [null, [Validators.required]],
    slotDuration: [null, [Validators.required]],
    maxAvailableDays: [null, [Validators.required]],
    status: [null, [Validators.required]],
    actionBy: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    updatedAt: [],
  });

  constructor(
    protected generationRuleService: GenerationRuleService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ generationRule }) => {
      if (generationRule.id === undefined) {
        const today = dayjs().startOf('day');
        generationRule.validFrom = today;
        generationRule.startSlotsTime = today;
        generationRule.endSlotsTime = today;
        generationRule.createdAt = today;
        generationRule.updatedAt = today;
      }

      this.updateForm(generationRule);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const generationRule = this.createFromForm();
    if (generationRule.id !== undefined) {
      this.subscribeToSaveResponse(this.generationRuleService.update(generationRule));
    } else {
      this.subscribeToSaveResponse(this.generationRuleService.create(generationRule));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGenerationRule>>): void {
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

  protected updateForm(generationRule: IGenerationRule): void {
    this.editForm.patchValue({
      id: generationRule.id,
      centerName: generationRule.centerName,
      validFrom: generationRule.validFrom ? generationRule.validFrom.format(DATE_TIME_FORMAT) : null,
      validTo: generationRule.validTo,
      daysOfWeek: generationRule.daysOfWeek,
      defaultCapacity: generationRule.defaultCapacity,
      startSlotsTime: generationRule.startSlotsTime ? generationRule.startSlotsTime.format(DATE_TIME_FORMAT) : null,
      endSlotsTime: generationRule.endSlotsTime ? generationRule.endSlotsTime.format(DATE_TIME_FORMAT) : null,
      slotDuration: generationRule.slotDuration,
      maxAvailableDays: generationRule.maxAvailableDays,
      status: generationRule.status,
      actionBy: generationRule.actionBy,
      createdAt: generationRule.createdAt ? generationRule.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: generationRule.updatedAt ? generationRule.updatedAt.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IGenerationRule {
    return {
      ...new GenerationRule(),
      id: this.editForm.get(['id'])!.value,
      centerName: this.editForm.get(['centerName'])!.value,
      validFrom: this.editForm.get(['validFrom'])!.value ? dayjs(this.editForm.get(['validFrom'])!.value, DATE_TIME_FORMAT) : undefined,
      validTo: this.editForm.get(['validTo'])!.value,
      daysOfWeek: this.editForm.get(['daysOfWeek'])!.value,
      defaultCapacity: this.editForm.get(['defaultCapacity'])!.value,
      startSlotsTime: this.editForm.get(['startSlotsTime'])!.value
        ? dayjs(this.editForm.get(['startSlotsTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      endSlotsTime: this.editForm.get(['endSlotsTime'])!.value
        ? dayjs(this.editForm.get(['endSlotsTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      slotDuration: this.editForm.get(['slotDuration'])!.value,
      maxAvailableDays: this.editForm.get(['maxAvailableDays'])!.value,
      status: this.editForm.get(['status'])!.value,
      actionBy: this.editForm.get(['actionBy'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? dayjs(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? dayjs(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }
}
