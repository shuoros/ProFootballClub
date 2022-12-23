import { ILeague } from '@/shared/model/league.model';
import { ICup } from '@/shared/model/cup.model';
import { IMessage } from '@/shared/model/message.model';

export interface IPrivateChat {
  id?: string;
  league?: ILeague | null;
  cup?: ICup | null;
  messages?: IMessage[] | null;
}

export class PrivateChat implements IPrivateChat {
  constructor(public id?: string, public league?: ILeague | null, public cup?: ICup | null, public messages?: IMessage[] | null) {}
}
