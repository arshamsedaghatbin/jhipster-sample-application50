jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ReservationService } from '../service/reservation.service';
import { IReservation, Reservation } from '../reservation.model';
import { ITimeSlot } from 'app/entities/time-slot/time-slot.model';
import { TimeSlotService } from 'app/entities/time-slot/service/time-slot.service';

import { ReservationUpdateComponent } from './reservation-update.component';

describe('Component Tests', () => {
  describe('Reservation Management Update Component', () => {
    let comp: ReservationUpdateComponent;
    let fixture: ComponentFixture<ReservationUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let reservationService: ReservationService;
    let timeSlotService: TimeSlotService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ReservationUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ReservationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReservationUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      reservationService = TestBed.inject(ReservationService);
      timeSlotService = TestBed.inject(TimeSlotService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call TimeSlot query and add missing value', () => {
        const reservation: IReservation = { id: 456 };
        const timeSlot: ITimeSlot = { id: 79607 };
        reservation.timeSlot = timeSlot;

        const timeSlotCollection: ITimeSlot[] = [{ id: 41637 }];
        jest.spyOn(timeSlotService, 'query').mockReturnValue(of(new HttpResponse({ body: timeSlotCollection })));
        const additionalTimeSlots = [timeSlot];
        const expectedCollection: ITimeSlot[] = [...additionalTimeSlots, ...timeSlotCollection];
        jest.spyOn(timeSlotService, 'addTimeSlotToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ reservation });
        comp.ngOnInit();

        expect(timeSlotService.query).toHaveBeenCalled();
        expect(timeSlotService.addTimeSlotToCollectionIfMissing).toHaveBeenCalledWith(timeSlotCollection, ...additionalTimeSlots);
        expect(comp.timeSlotsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const reservation: IReservation = { id: 456 };
        const timeSlot: ITimeSlot = { id: 60354 };
        reservation.timeSlot = timeSlot;

        activatedRoute.data = of({ reservation });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(reservation));
        expect(comp.timeSlotsSharedCollection).toContain(timeSlot);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Reservation>>();
        const reservation = { id: 123 };
        jest.spyOn(reservationService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ reservation });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: reservation }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(reservationService.update).toHaveBeenCalledWith(reservation);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Reservation>>();
        const reservation = new Reservation();
        jest.spyOn(reservationService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ reservation });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: reservation }));
        saveSubject.complete();

        // THEN
        expect(reservationService.create).toHaveBeenCalledWith(reservation);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Reservation>>();
        const reservation = { id: 123 };
        jest.spyOn(reservationService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ reservation });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(reservationService.update).toHaveBeenCalledWith(reservation);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackTimeSlotById', () => {
        it('Should return tracked TimeSlot primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackTimeSlotById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
