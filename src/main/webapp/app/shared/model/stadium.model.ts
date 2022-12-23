import { ITeam } from '@/shared/model/team.model';

import { Vehicle } from '@/shared/model/enumerations/vehicle.model';
import { Field } from '@/shared/model/enumerations/field.model';
import { Light } from '@/shared/model/enumerations/light.model';
import { Chair } from '@/shared/model/enumerations/chair.model';
import { Assistant } from '@/shared/model/enumerations/assistant.model';
import { BodyBuilding } from '@/shared/model/enumerations/body-building.model';
import { Doctor } from '@/shared/model/enumerations/doctor.model';
export interface IStadium {
  id?: string;
  name?: string;
  capacity?: number;
  ticket?: number;
  leader?: Date | null;
  vehicle?: Vehicle | null;
  field?: Field | null;
  light?: Light | null;
  chair?: Chair | null;
  assistant?: Assistant | null;
  bodyBuilding?: BodyBuilding | null;
  doctor?: Doctor | null;
  team?: ITeam | null;
}

export class Stadium implements IStadium {
  constructor(
    public id?: string,
    public name?: string,
    public capacity?: number,
    public ticket?: number,
    public leader?: Date | null,
    public vehicle?: Vehicle | null,
    public field?: Field | null,
    public light?: Light | null,
    public chair?: Chair | null,
    public assistant?: Assistant | null,
    public bodyBuilding?: BodyBuilding | null,
    public doctor?: Doctor | null,
    public team?: ITeam | null
  ) {}
}
