import { ITeam } from '@/shared/model/team.model';

export interface IFriendRequest {
  id?: string;
  from?: ITeam | null;
  tu?: ITeam | null;
}

export class FriendRequest implements IFriendRequest {
  constructor(public id?: string, public from?: ITeam | null, public tu?: ITeam | null) {}
}
