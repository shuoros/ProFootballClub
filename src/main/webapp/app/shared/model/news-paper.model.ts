import { ILeague } from '@/shared/model/league.model';
import { ICup } from '@/shared/model/cup.model';

export interface INewsPaper {
  id?: string;
  news?: string;
  league?: ILeague | null;
  cup?: ICup | null;
}

export class NewsPaper implements INewsPaper {
  constructor(public id?: string, public news?: string, public league?: ILeague | null, public cup?: ICup | null) {}
}
