import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { Router } from '@angular/router';
import { environment } from './../../environments/environment';
import { token } from '../models/token.model';
import { AppComponent } from '../app.component';
import { NgxSpinnerService } from 'ngx-spinner';
import { Usuario } from '../models/usuario.model';
import swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class SInicioSesionService {

  private urlBase: String = environment.URL_BASE;
  public _usuario: Usuario;
  public _ModToken: token;
  tiempoExpToken: any;
  public tokenstr: string = '';
  public EsUsuarioLogueado: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  public tokenVencido: boolean = false;
  public username: BehaviorSubject<string> = new BehaviorSubject<string>('');
  public userCompan: BehaviorSubject<string> = new BehaviorSubject<string>('');
  public EsInterno: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  public ROL: BehaviorSubject<string> = new BehaviorSubject<string>('');
  public ultimoInicioSesion: BehaviorSubject<string> = new BehaviorSubject<string>(''); // Almacena el último inicio de sesión
  public idCgnLogin: BehaviorSubject<string> = new BehaviorSubject<string>(''); // Almacena el idCgn del usuario logueado
  public fechaEdicion: BehaviorSubject<string> = new BehaviorSubject<string>(''); // Almacena la fecha de edicion de entidad
  public keyDownEvent;
  public mouseEvent;
  public scrollEvent;
  public clickEvent;


  // TODO Presentarlo en la vista

  constructor(private _http: HttpClient
    , private router: Router
    , private _AppComponent: AppComponent
    , private spinner: NgxSpinnerService
  ) {

    this._ModToken = new token();
  }

  // Función para obtener el token de autenticación
  public obtenerToken(tokenResponse: any): boolean {

    this._ModToken = tokenResponse;
    if (this._ModToken.authorities !== undefined) {
      this.ROL.next(this._ModToken.authorities[0].authority.toString());
    }
    if (this._ModToken.last_login_time !== undefined || this._ModToken.last_login_time !== null) {
      this.ultimoInicioSesion.next(this._ModToken.last_login_time);
    }

    try {
      // Recupera el token y lo guarda
      if (this._ModToken.access_token) {
        this.tokenstr = this._ModToken.access_token;
        this.watcherRefreshToken();
        return true;
      }
    } catch (e) {
      console.error(e);
    }
    this._ModToken = new token();
    return false;
  }

  // Valida los datos ingresados por el usuario interno
  public ValidarIngresoInt(usuario: string, contrasena: string): Observable<any> {

    const headersval = {
      'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Basic b3JlY2lwcm9jYXMtbmc6MHJjYXMtKjE5',
      'Access-Control-Allow-Headers': 'Content-Type'
    };

    const reqOptions = {
      headers: new HttpHeaders(headersval)
    };

    const body = new URLSearchParams();
    body.append('grant_type', 'password');
    body.append('username', usuario);
    body.append('password', contrasena);

    return this._http.post(this.urlBase + '/oauth/token', body.toString(), reqOptions);


  }

  // Valida los datos ingresados por el usuario externo
  public ValidarIngresoExt(inputNIT: string, inputIdCnt: string, inputContrasena: string): Observable<any> {

    this.idCgnLogin.next(inputIdCnt);

    const headersval = {
      'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Basic b3JlY2lwcm9jYXMtbmc6MHJjYXMtKjE5',
      'Access-Control-Allow-Headers': 'Content-Type'
    };

    const reqOptions = {
      headers: new HttpHeaders(headersval)
    };

    const body = new URLSearchParams();
    body.append('grant_type', 'password');
    body.append('username', inputNIT + '|' + inputIdCnt);
    body.append('password', inputContrasena);

    return this._http.post(this.urlBase + '/oauth/token', body.toString(), reqOptions);

  }

  // Cierra la sesión
  public CerrarSesion() {
    clearTimeout(this.tiempoExpToken);
    this.spinner.show();
    this.tokenVencido = false;
    this.tokenstr = '';
    let direccion;
    sessionStorage.getItem('TipoUsuario') === 'int' ? direccion = 'InicioSesionFNA' : direccion = 'InicioSesion';
    this.EsUsuarioLogueado.next(false);
    sessionStorage.clear();
    this.router.navigate([direccion]);
    this.spinner.hide();
    if (this.keyDownEvent) { this.keyDownEvent.unsubscribe(); }
    if (this.mouseEvent) { this.mouseEvent.unsubscribe(); }
    if (this.scrollEvent) { this.scrollEvent.unsubscribe(); }
    if (this.clickEvent) { this.clickEvent.unsubscribe(); }

    // window.location.replace(ruta);

  }

  // Petición al servidor que refresca el token
  public refrescarToken(): void {

    const headersval = {
      'Authorization': 'Basic b3JlY2lwcm9jYXMtbmc6MHJjYXMtKjE5',
      'Content-Type': 'application/x-www-form-urlencoded'
    };
    const reqOptions = {
      headers: new HttpHeaders(headersval)
    };
    const body = new URLSearchParams();
    body.set('grant_type', 'refresh_token');
    body.append('refresh_token', this._ModToken.refresh_token);

    this._http.post(this.urlBase + '/oauth/token', body.toString(), reqOptions).subscribe((resp: any) => {
      this.tokenVencido = false;
      this._ModToken = resp;
      this.watcherRefreshToken();
    }, error => {
      console.log(error);
      this.CerrarSesion();
    });
  }


  // Watcher de la actividad del usuario
  watcherRefreshToken() {

    // count necesario debido a la multiplicidad de clicks/scroll/keydown posibles
    let count = 0;

    // Primer timeout, depende del tiempo de caducidad del token, enviado desde el servidor
    this.tiempoExpToken = setTimeout(() => {
      console.log('%cSe venció el token', 'background:red;color:white;font-size:15px', this.mostrarhora());
      this.tokenVencido = true; // bandera para interceptor

      // Suscrito a AppComponent para poder acceder/monitorear a todas las vistas del DOM}
      // count = 1 para que solo entre una única actividad, que lanzará la petición refrescarToken()
      this.clickEvent = this._AppComponent.clicks$.subscribe(event$ => {
        if (count >= 1) { return; }
        count = 1;
        clearTimeout(this.tiempoExpToken); clearTimeout(tiempoExpRefresh);
        clearTimeout(alertCaducidadToken); swal.close(); this.refrescarToken();
        console.log('click');
        return;
      });
      this.scrollEvent = this._AppComponent.scroll$.subscribe(event$ => {
        if (count >= 1) { return; }
        count = 1;
        clearTimeout(this.tiempoExpToken); clearTimeout(tiempoExpRefresh);
        clearTimeout(alertCaducidadToken); swal.close(); this.refrescarToken();
        console.log('scroll');
        return;
      });
      this.keyDownEvent = this._AppComponent.keydown$.subscribe(event$ => {
        if (count >= 1) { return; }
        count = 1;
        clearTimeout(this.tiempoExpToken); clearTimeout(tiempoExpRefresh);
        clearTimeout(alertCaducidadToken); swal.close(); this.refrescarToken();
        console.log('keydown');
        return;
      });
      this.mouseEvent = this._AppComponent.mousemove$.subscribe(event$ => {
        if (count >= 1) { return; }
        count = 1;
        clearTimeout(this.tiempoExpToken); clearTimeout(tiempoExpRefresh);
        clearTimeout(alertCaducidadToken); swal.close(); this.refrescarToken();
        console.log('mouse');
        return;
      });

      // tiempoExpRefresh depende del tiempo de caducidad del RefreshToken
      const tiempoExpRefresh = setTimeout(() => {
        console.log('%cSe venció el refreshToken', 'background:blue;color:white;font-size:15px', this.mostrarhora());
        this.CerrarSesion();
      }, this._ModToken.refresh_token_expires_in * 1000);

      const tiempoEsperaAlerta = (this._ModToken.refresh_token_expires_in * 0.9 - this._ModToken.expires_in) * 1000;

      const alertCaducidadToken = setTimeout(() => {
        console.log('alerta');

        let timerInterval;
        swal({
          type: 'info',
          timer: this._ModToken.refresh_token_expires_in * 100,
          html: 'Tu sesión se cerrará en: <strong></strong> segundos.',
          onBeforeOpen: () => {
            console.log(this._ModToken.refresh_token_expires_in * 100);
            swal.showLoading();
            timerInterval = setInterval(() => {
              console.log(swal.getTimerLeft());
              let tiempo = (swal.getTimerLeft() / 1000).toString().split('.', 1);
              if (swal.getContent() === null) {
                clearInterval(timerInterval);
                console.log('nulo');
                return;
              }
              swal.getContent().querySelector('strong').textContent = tiempo.toString();
            }, 1000);
          },
          onClose: () => {
            clearInterval(timerInterval);
          },
          title: 'No hay actividad'
        });
      }, tiempoEsperaAlerta);

    }, this._ModToken.expires_in * 1000);

  }



  mostrarhora(): string {
    const f = new Date();
    const cad = f.getHours() + ':' + f.getMinutes() + ':' + f.getSeconds();
    window.status = cad;
    return cad;
  }

}
