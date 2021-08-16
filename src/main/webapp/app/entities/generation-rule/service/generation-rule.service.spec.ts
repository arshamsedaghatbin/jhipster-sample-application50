import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { GenerationRuleStatus } from 'app/entities/enumerations/generation-rule-status.model';
import { IGenerationRule, GenerationRule } from '../generation-rule.model';

import { GenerationRuleService } from './generation-rule.service';

describe('Service Tests', () => {
  describe('GenerationRule Service', () => {
    let service: GenerationRuleService;
    let httpMock: HttpTestingController;
    let elemDefault: IGenerationRule;
    let expectedResult: IGenerationRule | IGenerationRule[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(GenerationRuleService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        centerName: 'AAAAAAA',
        validFrom: currentDate,
        validTo: 'AAAAAAA',
        daysOfWeek: 0,
        defaultCapacity: 0,
        startSlotsTime: currentDate,
        endSlotsTime: currentDate,
        slotDuration: 0,
        maxAvailableDays: 0,
        status: GenerationRuleStatus.ACTIVE,
        actionBy: 'AAAAAAA',
        createdAt: currentDate,
        updatedAt: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            validFrom: currentDate.format(DATE_TIME_FORMAT),
            startSlotsTime: currentDate.format(DATE_TIME_FORMAT),
            endSlotsTime: currentDate.format(DATE_TIME_FORMAT),
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

      it('should create a GenerationRule', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            validFrom: currentDate.format(DATE_TIME_FORMAT),
            startSlotsTime: currentDate.format(DATE_TIME_FORMAT),
            endSlotsTime: currentDate.format(DATE_TIME_FORMAT),
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            validFrom: currentDate,
            startSlotsTime: currentDate,
            endSlotsTime: currentDate,
            createdAt: currentDate,
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new GenerationRule()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a GenerationRule', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            centerName: 'BBBBBB',
            validFrom: currentDate.format(DATE_TIME_FORMAT),
            validTo: 'BBBBBB',
            daysOfWeek: 1,
            defaultCapacity: 1,
            startSlotsTime: currentDate.format(DATE_TIME_FORMAT),
            endSlotsTime: currentDate.format(DATE_TIME_FORMAT),
            slotDuration: 1,
            maxAvailableDays: 1,
            status: 'BBBBBB',
            actionBy: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            validFrom: currentDate,
            startSlotsTime: currentDate,
            endSlotsTime: currentDate,
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

      it('should partial update a GenerationRule', () => {
        const patchObject = Object.assign(
          {
            daysOfWeek: 1,
            defaultCapacity: 1,
            startSlotsTime: currentDate.format(DATE_TIME_FORMAT),
            endSlotsTime: currentDate.format(DATE_TIME_FORMAT),
            slotDuration: 1,
          },
          new GenerationRule()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            validFrom: currentDate,
            startSlotsTime: currentDate,
            endSlotsTime: currentDate,
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

      it('should return a list of GenerationRule', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            centerName: 'BBBBBB',
            validFrom: currentDate.format(DATE_TIME_FORMAT),
            validTo: 'BBBBBB',
            daysOfWeek: 1,
            defaultCapacity: 1,
            startSlotsTime: currentDate.format(DATE_TIME_FORMAT),
            endSlotsTime: currentDate.format(DATE_TIME_FORMAT),
            slotDuration: 1,
            maxAvailableDays: 1,
            status: 'BBBBBB',
            actionBy: 'BBBBBB',
            createdAt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            validFrom: currentDate,
            startSlotsTime: currentDate,
            endSlotsTime: currentDate,
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

      it('should delete a GenerationRule', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addGenerationRuleToCollectionIfMissing', () => {
        it('should add a GenerationRule to an empty array', () => {
          const generationRule: IGenerationRule = { id: 123 };
          expectedResult = service.addGenerationRuleToCollectionIfMissing([], generationRule);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(generationRule);
        });

        it('should not add a GenerationRule to an array that contains it', () => {
          const generationRule: IGenerationRule = { id: 123 };
          const generationRuleCollection: IGenerationRule[] = [
            {
              ...generationRule,
            },
            { id: 456 },
          ];
          expectedResult = service.addGenerationRuleToCollectionIfMissing(generationRuleCollection, generationRule);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a GenerationRule to an array that doesn't contain it", () => {
          const generationRule: IGenerationRule = { id: 123 };
          const generationRuleCollection: IGenerationRule[] = [{ id: 456 }];
          expectedResult = service.addGenerationRuleToCollectionIfMissing(generationRuleCollection, generationRule);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(generationRule);
        });

        it('should add only unique GenerationRule to an array', () => {
          const generationRuleArray: IGenerationRule[] = [{ id: 123 }, { id: 456 }, { id: 61072 }];
          const generationRuleCollection: IGenerationRule[] = [{ id: 123 }];
          expectedResult = service.addGenerationRuleToCollectionIfMissing(generationRuleCollection, ...generationRuleArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const generationRule: IGenerationRule = { id: 123 };
          const generationRule2: IGenerationRule = { id: 456 };
          expectedResult = service.addGenerationRuleToCollectionIfMissing([], generationRule, generationRule2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(generationRule);
          expect(expectedResult).toContain(generationRule2);
        });

        it('should accept null and undefined values', () => {
          const generationRule: IGenerationRule = { id: 123 };
          expectedResult = service.addGenerationRuleToCollectionIfMissing([], null, generationRule, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(generationRule);
        });

        it('should return initial array if no GenerationRule is added', () => {
          const generationRuleCollection: IGenerationRule[] = [{ id: 123 }];
          expectedResult = service.addGenerationRuleToCollectionIfMissing(generationRuleCollection, undefined, null);
          expect(expectedResult).toEqual(generationRuleCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
