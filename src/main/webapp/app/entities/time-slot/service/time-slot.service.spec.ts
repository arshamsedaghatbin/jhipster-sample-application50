import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { AvailAbilityStatus } from 'app/entities/enumerations/avail-ability-status.model';
import { ITimeSlot, TimeSlot } from '../time-slot.model';

import { TimeSlotService } from './time-slot.service';

describe('Service Tests', () => {
  describe('TimeSlot Service', () => {
    let service: TimeSlotService;
    let httpMock: HttpTestingController;
    let elemDefault: ITimeSlot;
    let expectedResult: ITimeSlot | ITimeSlot[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TimeSlotService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        startTime: currentDate,
        endTime: currentDate,
        capacity: 0,
        remaining: 0,
        availabilityStatus: AvailAbilityStatus.AVAILABLE,
        centerName: 'AAAAAAA',
        actionBy: 'AAAAAAA',
        createdAt: currentDate,
        updatedAt: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            startTime: currentDate.format(DATE_TIME_FORMAT),
            endTime: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a TimeSlot', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            startTime: currentDate.format(DATE_TIME_FORMAT),
            endTime: currentDate.format(DATE_TIME_FORMAT),
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startTime: currentDate,
            endTime: currentDate,
            createdAt: currentDate,
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new TimeSlot()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TimeSlot', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            startTime: currentDate.format(DATE_TIME_FORMAT),
            endTime: currentDate.format(DATE_TIME_FORMAT),
            capacity: 1,
            remaining: 1,
            availabilityStatus: 'BBBBBB',
            centerName: 'BBBBBB',
            actionBy: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startTime: currentDate,
            endTime: currentDate,
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

      it('should partial update a TimeSlot', () => {
        const patchObject = Object.assign(
          {
            endTime: currentDate.format(DATE_TIME_FORMAT),
            remaining: 1,
            actionBy: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          new TimeSlot()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            startTime: currentDate,
            endTime: currentDate,
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

      it('should return a list of TimeSlot', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            startTime: currentDate.format(DATE_TIME_FORMAT),
            endTime: currentDate.format(DATE_TIME_FORMAT),
            capacity: 1,
            remaining: 1,
            availabilityStatus: 'BBBBBB',
            centerName: 'BBBBBB',
            actionBy: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startTime: currentDate,
            endTime: currentDate,
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

      it('should delete a TimeSlot', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTimeSlotToCollectionIfMissing', () => {
        it('should add a TimeSlot to an empty array', () => {
          const timeSlot: ITimeSlot = { id: 123 };
          expectedResult = service.addTimeSlotToCollectionIfMissing([], timeSlot);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(timeSlot);
        });

        it('should not add a TimeSlot to an array that contains it', () => {
          const timeSlot: ITimeSlot = { id: 123 };
          const timeSlotCollection: ITimeSlot[] = [
            {
              ...timeSlot,
            },
            { id: 456 },
          ];
          expectedResult = service.addTimeSlotToCollectionIfMissing(timeSlotCollection, timeSlot);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a TimeSlot to an array that doesn't contain it", () => {
          const timeSlot: ITimeSlot = { id: 123 };
          const timeSlotCollection: ITimeSlot[] = [{ id: 456 }];
          expectedResult = service.addTimeSlotToCollectionIfMissing(timeSlotCollection, timeSlot);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(timeSlot);
        });

        it('should add only unique TimeSlot to an array', () => {
          const timeSlotArray: ITimeSlot[] = [{ id: 123 }, { id: 456 }, { id: 80940 }];
          const timeSlotCollection: ITimeSlot[] = [{ id: 123 }];
          expectedResult = service.addTimeSlotToCollectionIfMissing(timeSlotCollection, ...timeSlotArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const timeSlot: ITimeSlot = { id: 123 };
          const timeSlot2: ITimeSlot = { id: 456 };
          expectedResult = service.addTimeSlotToCollectionIfMissing([], timeSlot, timeSlot2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(timeSlot);
          expect(expectedResult).toContain(timeSlot2);
        });

        it('should accept null and undefined values', () => {
          const timeSlot: ITimeSlot = { id: 123 };
          expectedResult = service.addTimeSlotToCollectionIfMissing([], null, timeSlot, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(timeSlot);
        });

        it('should return initial array if no TimeSlot is added', () => {
          const timeSlotCollection: ITimeSlot[] = [{ id: 123 }];
          expectedResult = service.addTimeSlotToCollectionIfMissing(timeSlotCollection, undefined, null);
          expect(expectedResult).toEqual(timeSlotCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
