import { Moment } from 'moment';
import { IAppuser } from 'app/shared/model/appuser.model';

export interface INotification {
  id?: number;
  title?: string;
  content?: any;
  seen?: boolean;
  date?: Moment;
  appuser?: IAppuser;
}

export class Notification implements INotification {
  constructor(
    public id?: number,
    public title?: string,
    public content?: any,
    public seen?: boolean,
    public date?: Moment,
    public appuser?: IAppuser
  ) {
    this.seen = this.seen || false;
  }
}
