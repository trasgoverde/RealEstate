import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IAppuser, Appuser } from 'app/shared/model/appuser.model';
import { AppuserService } from './appuser.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';

@Component({
  selector: 'jhi-appuser-update',
  templateUrl: './appuser-update.component.html',
})
export class AppuserUpdateComponent implements OnInit {
  isSaving = false;
  companies: ICompany[] = [];

  editForm = this.fb.group({
    id: [],
    firstName: [null, [Validators.required]],
    lastName: [null, [Validators.required]],
    phone: [null, [Validators.required]],
    email: [null, [Validators.required]],
    cif: [null, [Validators.required]],
    login: [null, [Validators.required]],
    company: [],
  });

  constructor(
    protected appuserService: AppuserService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appuser }) => {
      this.updateForm(appuser);

      this.companyService
        .query({ filter: 'appuser-is-null' })
        .pipe(
          map((res: HttpResponse<ICompany[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICompany[]) => {
          if (!appuser.company || !appuser.company.id) {
            this.companies = resBody;
          } else {
            this.companyService
              .find(appuser.company.id)
              .pipe(
                map((subRes: HttpResponse<ICompany>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICompany[]) => (this.companies = concatRes));
          }
        });
    });
  }

  updateForm(appuser: IAppuser): void {
    this.editForm.patchValue({
      id: appuser.id,
      firstName: appuser.firstName,
      lastName: appuser.lastName,
      phone: appuser.phone,
      email: appuser.email,
      cif: appuser.cif,
      login: appuser.login,
      company: appuser.company,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const appuser = this.createFromForm();
    if (appuser.id !== undefined) {
      this.subscribeToSaveResponse(this.appuserService.update(appuser));
    } else {
      this.subscribeToSaveResponse(this.appuserService.create(appuser));
    }
  }

  private createFromForm(): IAppuser {
    return {
      ...new Appuser(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      email: this.editForm.get(['email'])!.value,
      cif: this.editForm.get(['cif'])!.value,
      login: this.editForm.get(['login'])!.value,
      company: this.editForm.get(['company'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppuser>>): void {
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

  trackById(index: number, item: ICompany): any {
    return item.id;
  }
}
