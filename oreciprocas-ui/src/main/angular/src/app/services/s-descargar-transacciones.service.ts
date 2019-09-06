import { Injectable } from '@angular/core';
import { environment } from './../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SDescargarTransaccionesService {

  private urlBase: String = environment.URL_BASE;


  constructor(private _http: HttpClient) { }

  public descargarTransacciones(body): Observable<any> {
    console.log(body);
    return this._http.post(this.urlBase + '/api/transacciones/descargar', JSON.parse(body));
  }
}
