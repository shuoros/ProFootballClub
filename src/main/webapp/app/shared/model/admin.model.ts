import { IUser } from '@/shared/model/user.model';

export interface IAdmin {
  id?: string;
  adminId?: string;
  firstName?: string;
  lastName?: string;
  user?: IUser | null;
}

export class Admin implements IAdmin {
  constructor(
    public id?: string,
    public adminId?: string,
    public firstName?: string,
    public lastName?: string,
    public user?: IUser | null
  ) {}
}
