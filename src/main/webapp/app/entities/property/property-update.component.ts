import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProperty, Property } from 'app/shared/model/property.model';
import { PropertyService } from './property.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location/location.service';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';

type SelectableEntity = ILocation | IAppuser;

@Component({
  selector: 'jhi-property-update',
  templateUrl: './property-update.component.html',
})
export class PropertyUpdateComponent implements OnInit {
  isSaving = false;
  locations: ILocation[] = [];
  appusers: IAppuser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    price: [],
    description: [],
    buildingType: [],
    serviceType: [],
    ref: [null, [Validators.required]],
    visible: [],
    sold: [],
    terrace: [],
    m2: [],
    numberBedroom: [],
    elevator: [],
    furnished: [],
    pool: [],
    garage: [],
    numberWC: [],
    ac: [],
    location: [],
    appuser: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected propertyService: PropertyService,
    protected locationService: LocationService,
    protected appuserService: AppuserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ property }) => {
      this.updateForm(property);

      this.locationService
        .query({ filter: 'property-is-null' })
        .pipe(
          map((res: HttpResponse<ILocation[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ILocation[]) => {
          if (!property.location || !property.location.id) {
            this.locations = resBody;
          } else {
            this.locationService
              .find(property.location.id)
              .pipe(
                map((subRes: HttpResponse<ILocation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ILocation[]) => (this.locations = concatRes));
          }
        });

      this.appuserService.query().subscribe((res: HttpResponse<IAppuser[]>) => (this.appusers = res.body || []));
    });
  }

  updateForm(property: IProperty): void {
    this.editForm.patchValue({
      id: property.id,
      name: property.name,
      price: property.price,
      description: property.description,
      buildingType: property.buildingType,
      serviceType: property.serviceType,
      ref: property.ref,
      visible: property.visible,
      sold: property.sold,
      terrace: property.terrace,
      m2: property.m2,
      numberBedroom: property.numberBedroom,
      elevator: property.elevator,
      furnished: property.furnished,
      pool: property.pool,
      garage: property.garage,
      numberWC: property.numberWC,
      ac: property.ac,
      location: property.location,
      appuser: property.appuser,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('realEstateApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const property = this.createFromForm();
    if (property.id !== undefined) {
      this.subscribeToSaveResponse(this.propertyService.update(property));
    } else {
      this.subscribeToSaveResponse(this.propertyService.create(property));
    }
  }

  private createFromForm(): IProperty {
    return {
      ...new Property(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      price: this.editForm.get(['price'])!.value,
      description: this.editForm.get(['description'])!.value,
      buildingType: this.editForm.get(['buildingType'])!.value,
      serviceType: this.editForm.get(['serviceType'])!.value,
      ref: this.editForm.get(['ref'])!.value,
      visible: this.editForm.get(['visible'])!.value,
      sold: this.editForm.get(['sold'])!.value,
      terrace: this.editForm.get(['terrace'])!.value,
      m2: this.editForm.get(['m2'])!.value,
      numberBedroom: this.editForm.get(['numberBedroom'])!.value,
      elevator: this.editForm.get(['elevator'])!.value,
      furnished: this.editForm.get(['furnished'])!.value,
      pool: this.editForm.get(['pool'])!.value,
      garage: this.editForm.get(['garage'])!.value,
      numberWC: this.editForm.get(['numberWC'])!.value,
      ac: this.editForm.get(['ac'])!.value,
      location: this.editForm.get(['location'])!.value,
      appuser: this.editForm.get(['appuser'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProperty>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
