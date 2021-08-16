import * as dayjs from 'dayjs';
import { ITimeSlot } from 'app/entities/time-slot/time-slot.model';
import { ReservatorType } from 'app/entities/enumerations/reservator-type.model';
import { ReferenceType } from 'app/entities/enumerations/reference-type.model';
import { ReserveType } from 'app/entities/enumerations/reserve-type.model';
import { ReservationStatus } from 'app/entities/enumerations/reservation-status.model';

export interface IReservation {
  id?: number;
  reservatorId?: string;
  reservatorType?: ReservatorType;
  referenceId?: string;
  referenceType?: ReferenceType;
  reserveType?: ReserveType;
  status?: ReservationStatus;
  actionBy?: string;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs | null;
  timeSlot?: ITimeSlot | null;
}

export class Reservation implements IReservation {
  constructor(
    public id?: number,
    public reservatorId?: string,
    public reservatorType?: ReservatorType,
    public referenceId?: string,
    public referenceType?: ReferenceType,
    public reserveType?: ReserveType,
    public status?: ReservationStatus,
    public actionBy?: string,
    public createdAt?: dayjs.Dayjs,
    public updatedAt?: dayjs.Dayjs | null,
    public timeSlot?: ITimeSlot | null
  ) {}
}

export function getReservationIdentifier(reservation: IReservation): number | undefined {
  return reservation.id;
}
