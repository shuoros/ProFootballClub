import { ITeam } from '@/shared/model/team.model';
import { ILeague } from '@/shared/model/league.model';

export interface ILeagueBoard {
  id?: string;
  position?: number;
  win?: number | null;
  lose?: number | null;
  draw?: number | null;
  goalDifference?: number | null;
  points?: number | null;
  team?: ITeam | null;
  league?: ILeague | null;
}

export class LeagueBoard implements ILeagueBoard {
  constructor(
    public id?: string,
    public position?: number,
    public win?: number | null,
    public lose?: number | null,
    public draw?: number | null,
    public goalDifference?: number | null,
    public points?: number | null,
    public team?: ITeam | null,
    public league?: ILeague | null
  ) {}
}
