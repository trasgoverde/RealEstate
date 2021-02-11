import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPhoto, Photo } from 'app/shared/model/photo.model';
import { PhotoService } from './photo.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IProperty } from 'app/shared/model/property.model';
import { PropertyService } from 'app/entities/property/property.service';

@Component({
  selector: 'jhi-photo-update',
  templateUrl: './photo-update.component.html',
})
export class PhotoUpdateComponent implements OnInit {
  isSaving = false;
  properties: IProperty[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    created: [],
    image: [null, [Validators.required]],
    imageContentType: [],
    description: [],
    url: [],
    property: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected photoService: PhotoService,
    protected propertyService: PropertyService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ photo }) => {
      if (!photo.id) {
        const today = moment().startOf('day');
        photo.created = today;
      }

      this.updateForm(photo);

      this.propertyService.query().subscribe((res: HttpResponse<IProperty[]>) => (this.properties = res.body || []));
    });
  }

  updateForm(photo: IPhoto): void {
    this.editForm.patchValue({
      id: photo.id,
      name: photo.name,
      created: photo.created ? photo.created.format(DATE_TIME_FORMAT) : null,
      image: photo.image,
      imageContentType: photo.imageContentType,
      description: photo.description,
      url: photo.url,
      property: photo.property,
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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const photo = this.createFromForm();
    if (photo.id !== undefined) {
      this.subscribeToSaveResponse(this.photoService.update(photo));
    } else {
      this.subscribeToSaveResponse(this.photoService.create(photo));
    }
  }

  private createFromForm(): IPhoto {
    return {
      ...new Photo(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      description: this.editForm.get(['description'])!.value,
      url: this.editForm.get(['url'])!.value,
      property: this.editForm.get(['property'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhoto>>): void {
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

  trackById(index: number, item: IProperty): any {
    return item.id;
  }
}
