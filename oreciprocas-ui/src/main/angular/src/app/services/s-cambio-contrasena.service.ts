import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment';
@Injectable({
  providedIn: 'root'
})
export class SCambioContrasenaService {

  private urlBase: string = environment.URL_BASE;

  constructor(private _http: HttpClient) { }

  public RestablecerContrasena(UserNIT: string, IDCGN: string, CorreoEnt: string){
    return this._http.post(this.urlBase + '/publico/recuperar-clave',{
      nit: UserNIT,
      idCGN: IDCGN
    });
  }

  public CambiarContrasena(nit: string, idCGN: string, contrasenaActual: string, contrasenaNueva: string, confirmacionContrasena: string) {
    return this._http.post(this.urlBase + '/publico/cambiar-clave', {
      nit: nit,
      idCGN: idCGN,
      contrasenaActual: contrasenaActual,
      contrasenaNueva: contrasenaNueva,
      confirmacionContrasena: confirmacionContrasena
    });
    //TODO lógica cambiar contraseña
    //Debe volver al login y borrar caches/sessionstorage

  }

  public PrimeraContrasena(NuevaContrasena: string) {
    return this._http.post(this.urlBase + 'RestableceContrasena',{
      contrasena: NuevaContrasena
    });
  }

}
