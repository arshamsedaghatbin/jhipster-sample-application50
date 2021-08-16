jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { GenerationRuleService } from '../service/generation-rule.service';

import { GenerationRuleDeleteDialogComponent } from './generation-rule-delete-dialog.component';

describe('Component Tests', () => {
  describe('GenerationRule Management Delete Component', () => {
    let comp: GenerationRuleDeleteDialogComponent;
    let fixture: ComponentFixture<GenerationRuleDeleteDialogComponent>;
    let service: GenerationRuleService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [GenerationRuleDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(GenerationRuleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GenerationRuleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(GenerationRuleService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({})));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        jest.spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
