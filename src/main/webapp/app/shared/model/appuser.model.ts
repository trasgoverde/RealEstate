import { ICompany } from 'app/shared/model/company.model';

export interface IAppuser {
  id?: number;
  firstName?: string;
  lastName?: string;
  phone?: string;
  email?: string;
  cif?: string;
  login?: string;
  company?: ICompany;
}

export class Appuser implements IAppuser {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public phone?: string,
    public email?: string,
    public cif?: string,
    public login?: string,
    public company?: ICompany
  ) {}
}
