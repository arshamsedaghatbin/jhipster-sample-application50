import * as dayjs from 'dayjs';
import { ITimeSlot } from 'app/entities/time-slot/time-slot.model';
import { GenerationRuleStatus } from 'app/entities/enumerations/generation-rule-status.model';

export interface IGenerationRule {
  id?: number;
  centerName?: string;
  validFrom?: dayjs.Dayjs;
  validTo?: string;
  daysOfWeek?: number;
  defaultCapacity?: number;
  startSlotsTime?: dayjs.Dayjs;
  endSlotsTime?: dayjs.Dayjs;
  slotDuration?: number;
  maxAvailableDays?: number;
  status?: GenerationRuleStatus;
  actionBy?: string;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs | null;
  timeSlots?: ITimeSlot[] | null;
}

export class GenerationRule implements IGenerationRule {
  constructor(
    public id?: number,
    public centerName?: string,
    public validFrom?: dayjs.Dayjs,
    public validTo?: string,
    public daysOfWeek?: number,
    public defaultCapacity?: number,
    public startSlotsTime?: dayjs.Dayjs,
    public endSlotsTime?: dayjs.Dayjs,
    public slotDuration?: number,
    public maxAvailableDays?: number,
    public status?: GenerationRuleStatus,
    public actionBy?: string,
    public createdAt?: dayjs.Dayjs,
    public updatedAt?: dayjs.Dayjs | null,
    public timeSlots?: ITimeSlot[] | null
  ) {}
}

export function getGenerationRuleIdentifier(generationRule: IGenerationRule): number | undefined {
  return generationRule.id;
}
