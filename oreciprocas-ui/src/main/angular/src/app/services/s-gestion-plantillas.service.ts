import { Injectable } from '@angular/core';
import { environment } from './../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SPlantillasService {

  private urlBase: String = environment.URL_BASE;


  constructor(private _http: HttpClient) { }

  public getPlantillas(): Observable<any> {
    return this._http.get(this.urlBase + '/api/plantillas');
  }

  public actualizarPlantilla(body): Observable<any> {
    return this._http.put(this.urlBase + '/api/plantillas', body);
  }
}
