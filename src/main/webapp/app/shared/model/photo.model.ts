import { Moment } from 'moment';
import { IProperty } from 'app/shared/model/property.model';

export interface IPhoto {
  id?: number;
  name?: string;
  created?: Moment;
  imageContentType?: string;
  image?: any;
  description?: string;
  url?: string;
  property?: IProperty;
}

export class Photo implements IPhoto {
  constructor(
    public id?: number,
    public name?: string,
    public created?: Moment,
    public imageContentType?: string,
    public image?: any,
    public description?: string,
    public url?: string,
    public property?: IProperty
  ) {}
}
