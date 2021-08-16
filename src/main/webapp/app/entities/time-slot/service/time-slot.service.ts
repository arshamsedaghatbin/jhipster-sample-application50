import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITimeSlot, getTimeSlotIdentifier } from '../time-slot.model';

export type EntityResponseType = HttpResponse<ITimeSlot>;
export type EntityArrayResponseType = HttpResponse<ITimeSlot[]>;

@Injectable({ providedIn: 'root' })
export class TimeSlotService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/time-slots');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(timeSlot: ITimeSlot): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(timeSlot);
    return this.http
      .post<ITimeSlot>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(timeSlot: ITimeSlot): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(timeSlot);
    return this.http
      .put<ITimeSlot>(`${this.resourceUrl}/${getTimeSlotIdentifier(timeSlot) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(timeSlot: ITimeSlot): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(timeSlot);
    return this.http
      .patch<ITimeSlot>(`${this.resourceUrl}/${getTimeSlotIdentifier(timeSlot) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITimeSlot>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITimeSlot[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTimeSlotToCollectionIfMissing(timeSlotCollection: ITimeSlot[], ...timeSlotsToCheck: (ITimeSlot | null | undefined)[]): ITimeSlot[] {
    const timeSlots: ITimeSlot[] = timeSlotsToCheck.filter(isPresent);
    if (timeSlots.length > 0) {
      const timeSlotCollectionIdentifiers = timeSlotCollection.map(timeSlotItem => getTimeSlotIdentifier(timeSlotItem)!);
      const timeSlotsToAdd = timeSlots.filter(timeSlotItem => {
        const timeSlotIdentifier = getTimeSlotIdentifier(timeSlotItem);
        if (timeSlotIdentifier == null || timeSlotCollectionIdentifiers.includes(timeSlotIdentifier)) {
          return false;
        }
        timeSlotCollectionIdentifiers.push(timeSlotIdentifier);
        return true;
      });
      return [...timeSlotsToAdd, ...timeSlotCollection];
    }
    return timeSlotCollection;
  }

  protected convertDateFromClient(timeSlot: ITimeSlot): ITimeSlot {
    return Object.assign({}, timeSlot, {
      startTime: timeSlot.startTime?.isValid() ? timeSlot.startTime.toJSON() : undefined,
      endTime: timeSlot.endTime?.isValid() ? timeSlot.endTime.toJSON() : undefined,
      createdAt: timeSlot.createdAt?.isValid() ? timeSlot.createdAt.toJSON() : undefined,
      updatedAt: timeSlot.updatedAt?.isValid() ? timeSlot.updatedAt.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startTime = res.body.startTime ? dayjs(res.body.startTime) : undefined;
      res.body.endTime = res.body.endTime ? dayjs(res.body.endTime) : undefined;
      res.body.createdAt = res.body.createdAt ? dayjs(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? dayjs(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((timeSlot: ITimeSlot) => {
        timeSlot.startTime = timeSlot.startTime ? dayjs(timeSlot.startTime) : undefined;
        timeSlot.endTime = timeSlot.endTime ? dayjs(timeSlot.endTime) : undefined;
        timeSlot.createdAt = timeSlot.createdAt ? dayjs(timeSlot.createdAt) : undefined;
        timeSlot.updatedAt = timeSlot.updatedAt ? dayjs(timeSlot.updatedAt) : undefined;
      });
    }
    return res;
  }
}
