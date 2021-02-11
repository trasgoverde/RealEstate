import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IAppuser } from 'app/shared/model/appuser.model';

type EntityResponseType = HttpResponse<IAppuser>;
type EntityArrayResponseType = HttpResponse<IAppuser[]>;

@Injectable({ providedIn: 'root' })
export class AppuserService {
  public resourceUrl = SERVER_API_URL + 'api/appusers';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/appusers';

  constructor(protected http: HttpClient) {}

  create(appuser: IAppuser): Observable<EntityResponseType> {
    return this.http.post<IAppuser>(this.resourceUrl, appuser, { observe: 'response' });
  }

  update(appuser: IAppuser): Observable<EntityResponseType> {
    return this.http.put<IAppuser>(this.resourceUrl, appuser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAppuser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAppuser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAppuser[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
