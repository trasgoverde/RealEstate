import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { INotification, Notification } from 'app/shared/model/notification.model';
import { NotificationService } from './notification.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAppuser } from 'app/shared/model/appuser.model';
import { AppuserService } from 'app/entities/appuser/appuser.service';

@Component({
  selector: 'jhi-notification-update',
  templateUrl: './notification-update.component.html',
})
export class NotificationUpdateComponent implements OnInit {
  isSaving = false;
  appusers: IAppuser[] = [];

  editForm = this.fb.group({
    id: [],
    title: [],
    content: [],
    seen: [],
    date: [],
    appuser: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected notificationService: NotificationService,
    protected appuserService: AppuserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notification }) => {
      if (!notification.id) {
        const today = moment().startOf('day');
        notification.date = today;
      }

      this.updateForm(notification);

      this.appuserService.query().subscribe((res: HttpResponse<IAppuser[]>) => (this.appusers = res.body || []));
    });
  }

  updateForm(notification: INotification): void {
    this.editForm.patchValue({
      id: notification.id,
      title: notification.title,
      content: notification.content,
      seen: notification.seen,
      date: notification.date ? notification.date.format(DATE_TIME_FORMAT) : null,
      appuser: notification.appuser,
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
    const notification = this.createFromForm();
    if (notification.id !== undefined) {
      this.subscribeToSaveResponse(this.notificationService.update(notification));
    } else {
      this.subscribeToSaveResponse(this.notificationService.create(notification));
    }
  }

  private createFromForm(): INotification {
    return {
      ...new Notification(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      content: this.editForm.get(['content'])!.value,
      seen: this.editForm.get(['seen'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      appuser: this.editForm.get(['appuser'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotification>>): void {
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

  trackById(index: number, item: IAppuser): any {
    return item.id;
  }
}
