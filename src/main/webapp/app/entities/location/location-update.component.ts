import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILocation, Location } from 'app/shared/model/location.model';
import { LocationService } from './location.service';

@Component({
  selector: 'jhi-location-update',
  templateUrl: './location-update.component.html',
})
export class LocationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    ref: [],
    province: [null, [Validators.required]],
    town: [null, [Validators.required]],
    cp: [null, [Validators.required]],
    typeOfRoad: [null, [Validators.required]],
    nameRoad: [null, [Validators.required]],
    number: [null, [Validators.required]],
    apartment: [],
    building: [],
    door: [],
    stair: [],
    urlgmaps: [],
    latitude: [],
    longitude: [],
  });

  constructor(protected locationService: LocationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ location }) => {
      this.updateForm(location);
    });
  }

  updateForm(location: ILocation): void {
    this.editForm.patchValue({
      id: location.id,
      ref: location.ref,
      province: location.province,
      town: location.town,
      cp: location.cp,
      typeOfRoad: location.typeOfRoad,
      nameRoad: location.nameRoad,
      number: location.number,
      apartment: location.apartment,
      building: location.building,
      door: location.door,
      stair: location.stair,
      urlgmaps: location.urlgmaps,
      latitude: location.latitude,
      longitude: location.longitude,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const location = this.createFromForm();
    if (location.id !== undefined) {
      this.subscribeToSaveResponse(this.locationService.update(location));
    } else {
      this.subscribeToSaveResponse(this.locationService.create(location));
    }
  }

  private createFromForm(): ILocation {
    return {
      ...new Location(),
      id: this.editForm.get(['id'])!.value,
      ref: this.editForm.get(['ref'])!.value,
      province: this.editForm.get(['province'])!.value,
      town: this.editForm.get(['town'])!.value,
      cp: this.editForm.get(['cp'])!.value,
      typeOfRoad: this.editForm.get(['typeOfRoad'])!.value,
      nameRoad: this.editForm.get(['nameRoad'])!.value,
      number: this.editForm.get(['number'])!.value,
      apartment: this.editForm.get(['apartment'])!.value,
      building: this.editForm.get(['building'])!.value,
      door: this.editForm.get(['door'])!.value,
      stair: this.editForm.get(['stair'])!.value,
      urlgmaps: this.editForm.get(['urlgmaps'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocation>>): void {
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
}
