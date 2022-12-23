import { IPlayer } from '@/shared/model/player.model';

export interface ITransfer {
  id?: string;
  price?: number;
  password?: string | null;
  bought?: boolean | null;
  player?: IPlayer | null;
}

export class Transfer implements ITransfer {
  constructor(
    public id?: string,
    public price?: number,
    public password?: string | null,
    public bought?: boolean | null,
    public player?: IPlayer | null
  ) {
    this.bought = this.bought ?? false;
  }
}
