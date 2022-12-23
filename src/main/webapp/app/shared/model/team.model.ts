import { IStadium } from '@/shared/model/stadium.model';
import { IPlayer } from '@/shared/model/player.model';
import { IComposition } from '@/shared/model/composition.model';
import { ILeagueBoard } from '@/shared/model/league-board.model';
import { ICoach } from '@/shared/model/coach.model';

import { Country } from '@/shared/model/enumerations/country.model';
import { Gender } from '@/shared/model/enumerations/gender.model';
export interface ITeam {
  id?: string;
  name?: string;
  homeland?: Country;
  gender?: Gender;
  money?: number;
  points?: number;
  matches?: number;
  trophies?: number;
  readiness?: number;
  spirit?: number;
  fans?: number;
  fansSatisfaction?: number;
  stadium?: IStadium | null;
  players?: IPlayer[] | null;
  compositions?: IComposition[] | null;
  friends?: ITeam[] | null;
  leagueBoard?: ILeagueBoard | null;
  coach?: ICoach | null;
  team?: ITeam | null;
}

export class Team implements ITeam {
  constructor(
    public id?: string,
    public name?: string,
    public homeland?: Country,
    public gender?: Gender,
    public money?: number,
    public points?: number,
    public matches?: number,
    public trophies?: number,
    public readiness?: number,
    public spirit?: number,
    public fans?: number,
    public fansSatisfaction?: number,
    public stadium?: IStadium | null,
    public players?: IPlayer[] | null,
    public compositions?: IComposition[] | null,
    public friends?: ITeam[] | null,
    public leagueBoard?: ILeagueBoard | null,
    public coach?: ICoach | null,
    public team?: ITeam | null
  ) {}
}
