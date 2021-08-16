import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITimeSlot, TimeSlot } from '../time-slot.model';
import { TimeSlotService } from '../service/time-slot.service';

@Injectable({ providedIn: 'root' })
export class TimeSlotRoutingResolveService implements Resolve<ITimeSlot> {
  constructor(protected service: TimeSlotService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITimeSlot> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((timeSlot: HttpResponse<TimeSlot>) => {
          if (timeSlot.body) {
            return of(timeSlot.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TimeSlot());
  }
}
