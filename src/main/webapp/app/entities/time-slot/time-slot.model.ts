import * as dayjs from 'dayjs';
import { IReservation } from 'app/entities/reservation/reservation.model';
import { IGenerationRule } from 'app/entities/generation-rule/generation-rule.model';
import { AvailAbilityStatus } from 'app/entities/enumerations/avail-ability-status.model';

export interface ITimeSlot {
  id?: number;
  startTime?: dayjs.Dayjs;
  endTime?: dayjs.Dayjs;
  capacity?: number;
  remaining?: number;
  availabilityStatus?: AvailAbilityStatus;
  centerName?: string;
  actionBy?: string;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  reservations?: IReservation[] | null;
  generationRule?: IGenerationRule | null;
}

export class TimeSlot implements ITimeSlot {
  constructor(
    public id?: number,
    public startTime?: dayjs.Dayjs,
    public endTime?: dayjs.Dayjs,
    public capacity?: number,
    public remaining?: number,
    public availabilityStatus?: AvailAbilityStatus,
    public centerName?: string,
    public actionBy?: string,
    public createdAt?: dayjs.Dayjs,
    public updatedAt?: dayjs.Dayjs,
    public reservations?: IReservation[] | null,
    public generationRule?: IGenerationRule | null
  ) {}
}

export function getTimeSlotIdentifier(timeSlot: ITimeSlot): number | undefined {
  return timeSlot.id;
}
