import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ReservatorType } from 'app/entities/enumerations/reservator-type.model';
import { ReferenceType } from 'app/entities/enumerations/reference-type.model';
import { ReserveType } from 'app/entities/enumerations/reserve-type.model';
import { ReservationStatus } from 'app/entities/enumerations/reservation-status.model';
import { IReservation, Reservation } from '../reservation.model';

import { ReservationService } from './reservation.service';

describe('Service Tests', () => {
  describe('Reservation Service', () => {
    let service: ReservationService;
    let httpMock: HttpTestingController;
    let elemDefault: IReservation;
    let expectedResult: IReservation | IReservation[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ReservationService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        reservatorId: 'AAAAAAA',
        reservatorType: ReservatorType.CUSTOMER,
        referenceId: 'AAAAAAA',
        referenceType: ReferenceType.ORDER,
        reserveType: ReserveType.RESERVE,
        status: ReservationStatus.ACTIVE,
        actionBy: 'AAAAAAA',
        createdAt: currentDate,
        updatedAt: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Reservation', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new Reservation()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Reservation', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            reservatorId: 'BBBBBB',
            reservatorType: 'BBBBBB',
            referenceId: 'BBBBBB',
            referenceType: 'BBBBBB',
            reserveType: 'BBBBBB',
            status: 'BBBBBB',
            actionBy: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Reservation', () => {
        const patchObject = Object.assign(
          {
            reservatorId: 'BBBBBB',
            reservatorType: 'BBBBBB',
            referenceId: 'BBBBBB',
            status: 'BBBBBB',
            actionBy: 'BBBBBB',
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          new Reservation()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Reservation', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            reservatorId: 'BBBBBB',
            reservatorType: 'BBBBBB',
            referenceId: 'BBBBBB',
            referenceType: 'BBBBBB',
            reserveType: 'BBBBBB',
            status: 'BBBBBB',
            actionBy: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdAt: currentDate,
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Reservation', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addReservationToCollectionIfMissing', () => {
        it('should add a Reservation to an empty array', () => {
          const reservation: IReservation = { id: 123 };
          expectedResult = service.addReservationToCollectionIfMissing([], reservation);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(reservation);
        });

        it('should not add a Reservation to an array that contains it', () => {
          const reservation: IReservation = { id: 123 };
          const reservationCollection: IReservation[] = [
            {
              ...reservation,
            },
            { id: 456 },
          ];
          expectedResult = service.addReservationToCollectionIfMissing(reservationCollection, reservation);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Reservation to an array that doesn't contain it", () => {
          const reservation: IReservation = { id: 123 };
          const reservationCollection: IReservation[] = [{ id: 456 }];
          expectedResult = service.addReservationToCollectionIfMissing(reservationCollection, reservation);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(reservation);
        });

        it('should add only unique Reservation to an array', () => {
          const reservationArray: IReservation[] = [{ id: 123 }, { id: 456 }, { id: 3180 }];
          const reservationCollection: IReservation[] = [{ id: 123 }];
          expectedResult = service.addReservationToCollectionIfMissing(reservationCollection, ...reservationArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const reservation: IReservation = { id: 123 };
          const reservation2: IReservation = { id: 456 };
          expectedResult = service.addReservationToCollectionIfMissing([], reservation, reservation2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(reservation);
          expect(expectedResult).toContain(reservation2);
        });

        it('should accept null and undefined values', () => {
          const reservation: IReservation = { id: 123 };
          expectedResult = service.addReservationToCollectionIfMissing([], null, reservation, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(reservation);
        });

        it('should return initial array if no Reservation is added', () => {
          const reservationCollection: IReservation[] = [{ id: 123 }];
          expectedResult = service.addReservationToCollectionIfMissing(reservationCollection, undefined, null);
          expect(expectedResult).toEqual(reservationCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
