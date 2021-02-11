import { ICompany } from 'app/shared/model/company.model';
import { IProperty } from 'app/shared/model/property.model';
import { RoadType } from 'app/shared/model/enumerations/road-type.model';

export interface ILocation {
  id?: number;
  ref?: string;
  province?: string;
  town?: string;
  cp?: string;
  typeOfRoad?: RoadType;
  nameRoad?: string;
  number?: number;
  apartment?: number;
  building?: number;
  door?: number;
  stair?: string;
  urlgmaps?: string;
  latitude?: number;
  longitude?: number;
  company?: ICompany;
  property?: IProperty;
}

export class Location implements ILocation {
  constructor(
    public id?: number,
    public ref?: string,
    public province?: string,
    public town?: string,
    public cp?: string,
    public typeOfRoad?: RoadType,
    public nameRoad?: string,
    public number?: number,
    public apartment?: number,
    public building?: number,
    public door?: number,
    public stair?: string,
    public urlgmaps?: string,
    public latitude?: number,
    public longitude?: number,
    public company?: ICompany,
    public property?: IProperty
  ) {}
}
