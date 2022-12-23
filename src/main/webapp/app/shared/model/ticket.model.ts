import { ITeam } from '@/shared/model/team.model';
import { IMessage } from '@/shared/model/message.model';

export interface ITicket {
  id?: string;
  subject?: string;
  coach?: ITeam | null;
  messages?: IMessage[] | null;
}

export class Ticket implements ITicket {
  constructor(public id?: string, public subject?: string, public coach?: ITeam | null, public messages?: IMessage[] | null) {}
}
