import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { RealEstateTestModule } from '../../../test.module';
import { PropertyDetailComponent } from 'app/entities/property/property-detail.component';
import { Property } from 'app/shared/model/property.model';

describe('Component Tests', () => {
  describe('Property Management Detail Component', () => {
    let comp: PropertyDetailComponent;
    let fixture: ComponentFixture<PropertyDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ property: new Property(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RealEstateTestModule],
        declarations: [PropertyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PropertyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PropertyDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load property on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.property).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
