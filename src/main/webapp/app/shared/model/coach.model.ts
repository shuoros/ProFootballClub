import { IUser } from '@/shared/model/user.model';
import { ITeam } from '@/shared/model/team.model';

import { Plan } from '@/shared/model/enumerations/plan.model';
export interface ICoach {
  id?: string;
  name?: string;
  banned?: boolean;
  abandoned?: boolean;
  subscribed?: boolean;
  plan?: Plan;
  user?: IUser | null;
  teams?: ITeam[] | null;
}

export class Coach implements ICoach {
  constructor(
    public id?: string,
    public name?: string,
    public banned?: boolean,
    public abandoned?: boolean,
    public subscribed?: boolean,
    public plan?: Plan,
    public user?: IUser | null,
    public teams?: ITeam[] | null
  ) {
    this.banned = this.banned ?? false;
    this.abandoned = this.abandoned ?? false;
    this.subscribed = this.subscribed ?? false;
  }
}
