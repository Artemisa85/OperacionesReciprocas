import { Injectable } from '@angular/core';
import { environment } from './../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SDivisionPolitica {

  private urlBase: String = environment.URL_BASE;


  constructor(private _http: HttpClient) { }

  public getAllDepartamentos(): Observable<any> {
    return this._http.get(this.urlBase + '/api/departamentos?size=33');
  }

  public getMunicipiosXDepartamento(dpto): Observable<any> {
    return this._http.get(this.urlBase + `/api/departamentos/${dpto}/municipios`);
  }

}
