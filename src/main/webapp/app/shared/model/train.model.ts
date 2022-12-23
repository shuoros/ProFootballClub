import { IPlayer } from '@/shared/model/player.model';

import { Training } from '@/shared/model/enumerations/training.model';
export interface ITrain {
  id?: string;
  training?: Training | null;
  finishes?: Date | null;
  player?: IPlayer | null;
}

export class Train implements ITrain {
  constructor(public id?: string, public training?: Training | null, public finishes?: Date | null, public player?: IPlayer | null) {}
}
