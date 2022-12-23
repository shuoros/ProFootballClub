import { IPlayer } from '@/shared/model/player.model';
import { IComposition } from '@/shared/model/composition.model';

import { Post } from '@/shared/model/enumerations/post.model';
export interface IPlayerArrange {
  id?: string;
  post?: Post | null;
  player?: IPlayer | null;
  composition?: IComposition | null;
}

export class PlayerArrange implements IPlayerArrange {
  constructor(public id?: string, public post?: Post | null, public player?: IPlayer | null, public composition?: IComposition | null) {}
}
