import { ITeam } from '@/shared/model/team.model';
import { IComposition } from '@/shared/model/composition.model';
import { IPlayer } from '@/shared/model/player.model';
import { ILeague } from '@/shared/model/league.model';
import { ICupBoard } from '@/shared/model/cup-board.model';

import { Weather } from '@/shared/model/enumerations/weather.model';
import { MatchType } from '@/shared/model/enumerations/match-type.model';
export interface IMatch {
  id?: string;
  date?: Date;
  weather?: Weather;
  hostGoals?: number | null;
  guestGoals?: number | null;
  events?: string | null;
  type?: MatchType | null;
  host?: ITeam | null;
  guest?: ITeam | null;
  hostComposition?: IComposition | null;
  guestComposition?: IComposition | null;
  bestPlayer?: IPlayer | null;
  league?: ILeague | null;
  cupBoard?: ICupBoard | null;
}

export class Match implements IMatch {
  constructor(
    public id?: string,
    public date?: Date,
    public weather?: Weather,
    public hostGoals?: number | null,
    public guestGoals?: number | null,
    public events?: string | null,
    public type?: MatchType | null,
    public host?: ITeam | null,
    public guest?: ITeam | null,
    public hostComposition?: IComposition | null,
    public guestComposition?: IComposition | null,
    public bestPlayer?: IPlayer | null,
    public league?: ILeague | null,
    public cupBoard?: ICupBoard | null
  ) {}
}
