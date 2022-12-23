import { ITeam } from '@/shared/model/team.model';
import { IMessage } from '@/shared/model/message.model';

export interface IPublicChat {
  id?: string;
  from?: ITeam | null;
  tu?: ITeam | null;
  messages?: IMessage[] | null;
}

export class PublicChat implements IPublicChat {
  constructor(public id?: string, public from?: ITeam | null, public tu?: ITeam | null, public messages?: IMessage[] | null) {}
}
