import { IMatch } from '@/shared/model/match.model';
import { ICup } from '@/shared/model/cup.model';

export interface ICupBoard {
  id?: string;
  level?: number;
  matches?: IMatch[] | null;
  cup?: ICup | null;
}

export class CupBoard implements ICupBoard {
  constructor(public id?: string, public level?: number, public matches?: IMatch[] | null, public cup?: ICup | null) {}
}
