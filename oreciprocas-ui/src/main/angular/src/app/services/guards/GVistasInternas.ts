import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CanActivate } from '@angular/router';
import { SInicioSesionService } from '../s-inicio-sesion.service';
import { Utils } from '../../utils/Utils';

//Guardián para proteger las vistas internas del aplicativo. Ver app.routes.ts
@Injectable()
export class GVistasInternas implements CanActivate {

    private ruta;

    constructor(
        private sInicioSesion: SInicioSesionService
        , private router: Router
        , private _utils: Utils) { }

    canActivate() {
        // Si el usuario no está logeado se enviará al login
        if (this._utils.EstaLogueado())
            return true;

        this.ruta="/InicioSesion/"+sessionStorage.getItem('TipoUsuario');
        this.router.navigate([this.ruta]);
        this.sInicioSesion.CerrarSesion(); 
        return false;
        // return true; //Para acceder sin sesion
    }

}
