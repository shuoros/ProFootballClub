import { ITeam } from '@/shared/model/team.model';

import { Gender } from '@/shared/model/enumerations/gender.model';
import { Country } from '@/shared/model/enumerations/country.model';
import { PlayerStatus } from '@/shared/model/enumerations/player-status.model';
import { Post } from '@/shared/model/enumerations/post.model';
export interface IPlayer {
  id?: string;
  firstName?: string;
  lastName?: string;
  gender?: Gender;
  country?: Country;
  age?: number;
  status?: PlayerStatus;
  post?: Post;
  totalPower?: number;
  goalkeeping?: number;
  defence?: number;
  tackling?: number;
  passing?: number;
  teamSkill?: number;
  ballSkill?: number;
  shooting?: number;
  longShooting?: number;
  dribbling?: number;
  technique?: number;
  confidence?: number;
  team?: ITeam | null;
}

export class Player implements IPlayer {
  constructor(
    public id?: string,
    public firstName?: string,
    public lastName?: string,
    public gender?: Gender,
    public country?: Country,
    public age?: number,
    public status?: PlayerStatus,
    public post?: Post,
    public totalPower?: number,
    public goalkeeping?: number,
    public defence?: number,
    public tackling?: number,
    public passing?: number,
    public teamSkill?: number,
    public ballSkill?: number,
    public shooting?: number,
    public longShooting?: number,
    public dribbling?: number,
    public technique?: number,
    public confidence?: number,
    public team?: ITeam | null
  ) {}
}
