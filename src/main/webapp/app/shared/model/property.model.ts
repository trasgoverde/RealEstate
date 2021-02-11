import { ILocation } from 'app/shared/model/location.model';
import { IAppuser } from 'app/shared/model/appuser.model';
import { BuildingType } from 'app/shared/model/enumerations/building-type.model';
import { ServiceType } from 'app/shared/model/enumerations/service-type.model';

export interface IProperty {
  id?: number;
  name?: string;
  price?: number;
  description?: any;
  buildingType?: BuildingType;
  serviceType?: ServiceType;
  ref?: string;
  visible?: boolean;
  sold?: boolean;
  terrace?: boolean;
  m2?: number;
  numberBedroom?: number;
  elevator?: boolean;
  furnished?: boolean;
  pool?: boolean;
  garage?: boolean;
  numberWC?: number;
  ac?: boolean;
  location?: ILocation;
  appuser?: IAppuser;
}

export class Property implements IProperty {
  constructor(
    public id?: number,
    public name?: string,
    public price?: number,
    public description?: any,
    public buildingType?: BuildingType,
    public serviceType?: ServiceType,
    public ref?: string,
    public visible?: boolean,
    public sold?: boolean,
    public terrace?: boolean,
    public m2?: number,
    public numberBedroom?: number,
    public elevator?: boolean,
    public furnished?: boolean,
    public pool?: boolean,
    public garage?: boolean,
    public numberWC?: number,
    public ac?: boolean,
    public location?: ILocation,
    public appuser?: IAppuser
  ) {
    this.visible = this.visible || false;
    this.sold = this.sold || false;
    this.terrace = this.terrace || false;
    this.elevator = this.elevator || false;
    this.furnished = this.furnished || false;
    this.pool = this.pool || false;
    this.garage = this.garage || false;
    this.ac = this.ac || false;
  }
}
