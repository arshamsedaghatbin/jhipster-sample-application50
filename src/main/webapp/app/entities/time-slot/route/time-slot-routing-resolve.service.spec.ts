jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITimeSlot, TimeSlot } from '../time-slot.model';
import { TimeSlotService } from '../service/time-slot.service';

import { TimeSlotRoutingResolveService } from './time-slot-routing-resolve.service';

describe('Service Tests', () => {
  describe('TimeSlot routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TimeSlotRoutingResolveService;
    let service: TimeSlotService;
    let resultTimeSlot: ITimeSlot | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TimeSlotRoutingResolveService);
      service = TestBed.inject(TimeSlotService);
      resultTimeSlot = undefined;
    });

    describe('resolve', () => {
      it('should return ITimeSlot returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTimeSlot = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTimeSlot).toEqual({ id: 123 });
      });

      it('should return new ITimeSlot if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTimeSlot = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTimeSlot).toEqual(new TimeSlot());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TimeSlot })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTimeSlot = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTimeSlot).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
