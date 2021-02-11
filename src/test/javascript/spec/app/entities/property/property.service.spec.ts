import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PropertyService } from 'app/entities/property/property.service';
import { IProperty, Property } from 'app/shared/model/property.model';
import { BuildingType } from 'app/shared/model/enumerations/building-type.model';
import { ServiceType } from 'app/shared/model/enumerations/service-type.model';

describe('Service Tests', () => {
  describe('Property Service', () => {
    let injector: TestBed;
    let service: PropertyService;
    let httpMock: HttpTestingController;
    let elemDefault: IProperty;
    let expectedResult: IProperty | IProperty[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PropertyService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Property(
        0,
        'AAAAAAA',
        0,
        'AAAAAAA',
        BuildingType.HOUSE,
        ServiceType.RENT,
        'AAAAAAA',
        false,
        false,
        false,
        0,
        0,
        false,
        false,
        false,
        false,
        0,
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Property', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Property()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Property', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            price: 1,
            description: 'BBBBBB',
            buildingType: 'BBBBBB',
            serviceType: 'BBBBBB',
            ref: 'BBBBBB',
            visible: true,
            sold: true,
            terrace: true,
            m2: 1,
            numberBedroom: 1,
            elevator: true,
            furnished: true,
            pool: true,
            garage: true,
            numberWC: 1,
            ac: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Property', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            price: 1,
            description: 'BBBBBB',
            buildingType: 'BBBBBB',
            serviceType: 'BBBBBB',
            ref: 'BBBBBB',
            visible: true,
            sold: true,
            terrace: true,
            m2: 1,
            numberBedroom: 1,
            elevator: true,
            furnished: true,
            pool: true,
            garage: true,
            numberWC: 1,
            ac: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Property', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
