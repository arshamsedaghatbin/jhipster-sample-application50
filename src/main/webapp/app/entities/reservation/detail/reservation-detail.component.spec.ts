import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ReservationDetailComponent } from './reservation-detail.component';

describe('Component Tests', () => {
  describe('Reservation Management Detail Component', () => {
    let comp: ReservationDetailComponent;
    let fixture: ComponentFixture<ReservationDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ReservationDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ reservation: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ReservationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReservationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load reservation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reservation).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
