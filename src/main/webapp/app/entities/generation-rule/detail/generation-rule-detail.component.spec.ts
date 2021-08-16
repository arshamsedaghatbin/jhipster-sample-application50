import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GenerationRuleDetailComponent } from './generation-rule-detail.component';

describe('Component Tests', () => {
  describe('GenerationRule Management Detail Component', () => {
    let comp: GenerationRuleDetailComponent;
    let fixture: ComponentFixture<GenerationRuleDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [GenerationRuleDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ generationRule: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(GenerationRuleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GenerationRuleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load generationRule on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.generationRule).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
