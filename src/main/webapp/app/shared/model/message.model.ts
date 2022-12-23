import { ITeam } from '@/shared/model/team.model';
import { ITicket } from '@/shared/model/ticket.model';
import { IPrivateChat } from '@/shared/model/private-chat.model';
import { IPublicChat } from '@/shared/model/public-chat.model';

export interface IMessage {
  id?: string;
  message?: string;
  coach?: ITeam | null;
  ticket?: ITicket | null;
  privateChat?: IPrivateChat | null;
  publicChat?: IPublicChat | null;
}

export class Message implements IMessage {
  constructor(
    public id?: string,
    public message?: string,
    public coach?: ITeam | null,
    public ticket?: ITicket | null,
    public privateChat?: IPrivateChat | null,
    public publicChat?: IPublicChat | null
  ) {}
}
