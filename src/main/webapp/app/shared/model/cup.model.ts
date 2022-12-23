import { IPlayer } from '@/shared/model/player.model';
import { INewsPaper } from '@/shared/model/news-paper.model';
import { ICupBoard } from '@/shared/model/cup-board.model';

import { CupType } from '@/shared/model/enumerations/cup-type.model';
export interface ICup {
  id?: string;
  type?: CupType;
  events?: string | null;
  finished?: boolean | null;
  entrance?: number;
  start?: Date | null;
  goalScorer?: IPlayer | null;
  newsPaper?: INewsPaper | null;
  cupBoards?: ICupBoard[] | null;
}

export class Cup implements ICup {
  constructor(
    public id?: string,
    public type?: CupType,
    public events?: string | null,
    public finished?: boolean | null,
    public entrance?: number,
    public start?: Date | null,
    public goalScorer?: IPlayer | null,
    public newsPaper?: INewsPaper | null,
    public cupBoards?: ICupBoard[] | null
  ) {
    this.finished = this.finished ?? false;
  }
}
