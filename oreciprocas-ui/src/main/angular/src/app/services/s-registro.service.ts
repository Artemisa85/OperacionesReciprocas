import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../environments/environment';
import { Observable } from 'rxjs';
import { error } from 'util';

@Injectable({
  providedIn: 'root'
})
export class SRegistroService implements OnInit {

  private urlBase:String = environment.URL_BASE;

  res: boolean;

  constructor(public _http: HttpClient) {
  }

  //Para POST
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  ConsultarEntidad(NIT:string, IdCGN:string): any {

    return this._http.get(this.urlBase + "/publico/entidades", {
      params: {
        nit: NIT,
        idCGN: IdCGN
      }
    })
  }


  public RegistrarUsuario(inputUserNIT: string, inputIdCGN: string, inputCorreo: string):any {
    return this._http.post(this.urlBase + '/publico/registro', { 
      nit: inputUserNIT, 
      idCGN: inputIdCGN,
      correo: inputCorreo
     });



    // .subscribe(
      //   (resp: any) => {
      //     console.log(resp);
      //     //Si responde bien todo va a estar en resp
      //     this.res = true;
      //   }, e => { //error

      //     console.error("el error fue: ", e);
      //     this.res = false;
      //   }, () => { //end

      //     console.info("Finalizó la autenticación");
      //   });
  }

  ngOnInit() {
    // this.urlBase = "";
  }

}