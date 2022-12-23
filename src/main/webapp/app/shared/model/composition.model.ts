import { IPlayer } from '@/shared/model/player.model';
import { IPlayerArrange } from '@/shared/model/player-arrange.model';
import { ITeam } from '@/shared/model/team.model';

import { Arrange } from '@/shared/model/enumerations/arrange.model';
import { Strategy } from '@/shared/model/enumerations/strategy.model';
export interface IComposition {
  id?: string;
  d3fault?: boolean;
  arrange?: Arrange;
  strategy?: Strategy;
  defence?: number;
  shortPass?: number;
  violence?: number;
  capitan?: IPlayer | null;
  playerArranges?: IPlayerArrange[] | null;
  team?: ITeam | null;
}

export class Composition implements IComposition {
  constructor(
    public id?: string,
    public d3fault?: boolean,
    public arrange?: Arrange,
    public strategy?: Strategy,
    public defence?: number,
    public shortPass?: number,
    public violence?: number,
    public capitan?: IPlayer | null,
    public playerArranges?: IPlayerArrange[] | null,
    public team?: ITeam | null
  ) {
    this.d3fault = this.d3fault ?? false;
  }
}
