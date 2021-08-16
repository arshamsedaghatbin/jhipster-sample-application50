jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { GenerationRuleService } from '../service/generation-rule.service';
import { IGenerationRule, GenerationRule } from '../generation-rule.model';

import { GenerationRuleUpdateComponent } from './generation-rule-update.component';

describe('Component Tests', () => {
  describe('GenerationRule Management Update Component', () => {
    let comp: GenerationRuleUpdateComponent;
    let fixture: ComponentFixture<GenerationRuleUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let generationRuleService: GenerationRuleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [GenerationRuleUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(GenerationRuleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GenerationRuleUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      generationRuleService = TestBed.inject(GenerationRuleService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const generationRule: IGenerationRule = { id: 456 };

        activatedRoute.data = of({ generationRule });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(generationRule));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<GenerationRule>>();
        const generationRule = { id: 123 };
        jest.spyOn(generationRuleService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ generationRule });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: generationRule }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(generationRuleService.update).toHaveBeenCalledWith(generationRule);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<GenerationRule>>();
        const generationRule = new GenerationRule();
        jest.spyOn(generationRuleService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ generationRule });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: generationRule }));
        saveSubject.complete();

        // THEN
        expect(generationRuleService.create).toHaveBeenCalledWith(generationRule);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<GenerationRule>>();
        const generationRule = { id: 123 };
        jest.spyOn(generationRuleService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ generationRule });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(generationRuleService.update).toHaveBeenCalledWith(generationRule);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
