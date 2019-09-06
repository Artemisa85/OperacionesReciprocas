import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from './../../environments/environment';
import { Observable } from 'rxjs';
import { RequestOptions, ResponseContentType } from '@angular/http';

@Injectable({
  providedIn: 'root'
})
export class SOperacionesReciprocasService {

  private urlBase: string = environment.URL_BASE;

  constructor(private _http: HttpClient) { }
  // this.msgErrorPeso=`El tamaño del archivo supera el tamaño permitido: ${this.tamMaxArch/1048576}MB.`;

  public ConsultarDetallesOp(idOperacion: string): Observable<any> {
    return this._http.get(`${this.urlBase}/api/transacciones/${idOperacion}`);
  }

  public ConsultarTransacciones(body, size: string, pageNumber: string): Observable<any>{

    let appHeaders = new HttpHeaders();
    appHeaders.append('Content-Type', 'application/x-www-form-urlencoded');

    let parametros = new HttpParams().append('size', size).append('page', pageNumber);

    return this._http.post(this.urlBase + '/api/transacciones', JSON.parse(body), {headers: appHeaders, params: parametros});
  }

  public CargarFiltrosTransacciones(): Observable<any>  {

    let appHeaders = new HttpHeaders();
    appHeaders.append('Content-Type', 'application/json');

    return this._http.get(this.urlBase + '/api/transacciones/filtros-consulta', {headers: appHeaders});
  }


  public consultarComentarios(idOpRecip: number): Observable<any> {
    return this._http.get(this.urlBase + '/api/transacciones/' + idOpRecip + '/comentarios');
  }

  public descargarAdjunto(Adjunto): Observable<any> {
    return this._http
      .get(Adjunto.url,
        {
          headers: new HttpHeaders().append('Content-Type', Adjunto.tipoMime),
          responseType: 'blob',
          observe: 'body'
        });
  }

}



