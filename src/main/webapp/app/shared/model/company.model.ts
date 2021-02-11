import { ILocation } from 'app/shared/model/location.model';

export interface ICompany {
  id?: number;
  name?: string;
  phone?: string;
  email?: string;
  cif?: string;
  location?: ILocation;
}

export class Company implements ICompany {
  constructor(
    public id?: number,
    public name?: string,
    public phone?: string,
    public email?: string,
    public cif?: string,
    public location?: ILocation
  ) {}
}
