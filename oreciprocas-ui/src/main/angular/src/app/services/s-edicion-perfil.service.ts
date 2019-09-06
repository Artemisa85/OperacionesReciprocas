import { Injectable } from '@angular/core';
import { environment } from './../../environments/environment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SEdicionPerfilService {


  private urlBase: String = environment.URL_BASE;


  constructor(private _http: HttpClient) { }

  public actualizarDatos(body): Observable<any> {
    body.fechaModificacion = null;
    return this._http.put(this.urlBase + '/api/entidades', body);
  }

  crearEntidad(body: any): Observable<any> {
    return this._http.post(this.urlBase + '/api/entidades', body);
  }

  public getDetalleByIdCgn(param): Observable<any> {
    return this._http.get(this.urlBase + `/api/entidades/${param}`);
  }

  public getEntidades(size, page, body: any): Observable<any> {
    const parametros = new HttpParams().append('size', size).append('page', page);
    return this._http.post(this.urlBase + `/api/entidades/buscar`, body, {params: parametros});
  }

}
