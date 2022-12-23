import { IPlayer } from '@/shared/model/player.model';
import { INewsPaper } from '@/shared/model/news-paper.model';
import { IMatch } from '@/shared/model/match.model';
import { ILeagueBoard } from '@/shared/model/league-board.model';

export interface ILeague {
  id?: string;
  clazz?: number;
  events?: string | null;
  finished?: boolean | null;
  start?: Date | null;
  goalScorer?: IPlayer | null;
  newsPaper?: INewsPaper | null;
  matches?: IMatch[] | null;
  leagueBoards?: ILeagueBoard[] | null;
}

export class League implements ILeague {
  constructor(
    public id?: string,
    public clazz?: number,
    public events?: string | null,
    public finished?: boolean | null,
    public start?: Date | null,
    public goalScorer?: IPlayer | null,
    public newsPaper?: INewsPaper | null,
    public matches?: IMatch[] | null,
    public leagueBoards?: ILeagueBoard[] | null
  ) {
    this.finished = this.finished ?? false;
  }
}
