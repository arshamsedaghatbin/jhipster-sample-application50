jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TimeSlotService } from '../service/time-slot.service';
import { ITimeSlot, TimeSlot } from '../time-slot.model';
import { IGenerationRule } from 'app/entities/generation-rule/generation-rule.model';
import { GenerationRuleService } from 'app/entities/generation-rule/service/generation-rule.service';

import { TimeSlotUpdateComponent } from './time-slot-update.component';

describe('Component Tests', () => {
  describe('TimeSlot Management Update Component', () => {
    let comp: TimeSlotUpdateComponent;
    let fixture: ComponentFixture<TimeSlotUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let timeSlotService: TimeSlotService;
    let generationRuleService: GenerationRuleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TimeSlotUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TimeSlotUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TimeSlotUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      timeSlotService = TestBed.inject(TimeSlotService);
      generationRuleService = TestBed.inject(GenerationRuleService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call GenerationRule query and add missing value', () => {
        const timeSlot: ITimeSlot = { id: 456 };
        const generationRule: IGenerationRule = { id: 17658 };
        timeSlot.generationRule = generationRule;

        const generationRuleCollection: IGenerationRule[] = [{ id: 55466 }];
        jest.spyOn(generationRuleService, 'query').mockReturnValue(of(new HttpResponse({ body: generationRuleCollection })));
        const additionalGenerationRules = [generationRule];
        const expectedCollection: IGenerationRule[] = [...additionalGenerationRules, ...generationRuleCollection];
        jest.spyOn(generationRuleService, 'addGenerationRuleToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ timeSlot });
        comp.ngOnInit();

        expect(generationRuleService.query).toHaveBeenCalled();
        expect(generationRuleService.addGenerationRuleToCollectionIfMissing).toHaveBeenCalledWith(
          generationRuleCollection,
          ...additionalGenerationRules
        );
        expect(comp.generationRulesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const timeSlot: ITimeSlot = { id: 456 };
        const generationRule: IGenerationRule = { id: 22821 };
        timeSlot.generationRule = generationRule;

        activatedRoute.data = of({ timeSlot });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(timeSlot));
        expect(comp.generationRulesSharedCollection).toContain(generationRule);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<TimeSlot>>();
        const timeSlot = { id: 123 };
        jest.spyOn(timeSlotService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ timeSlot });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: timeSlot }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(timeSlotService.update).toHaveBeenCalledWith(timeSlot);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<TimeSlot>>();
        const timeSlot = new TimeSlot();
        jest.spyOn(timeSlotService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ timeSlot });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: timeSlot }));
        saveSubject.complete();

        // THEN
        expect(timeSlotService.create).toHaveBeenCalledWith(timeSlot);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<TimeSlot>>();
        const timeSlot = { id: 123 };
        jest.spyOn(timeSlotService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ timeSlot });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(timeSlotService.update).toHaveBeenCalledWith(timeSlot);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackGenerationRuleById', () => {
        it('Should return tracked GenerationRule primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackGenerationRuleById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
