import { Injectable } from "@angular/core";
import { SInicioSesionService } from "../services/s-inicio-sesion.service";
import { Router } from "@angular/router";

@Injectable({
    providedIn: 'root'
})
export class Utils {

    private ruta: string;
    public ParamURL: string;

    constructor(
        private sInicioSesion: SInicioSesionService,
        private router: Router
    ) {
    }

    //Verifica si el usuario está logueado en el sistema
    public EstaLogueado() {
        if (this.sInicioSesion.EsUsuarioLogueado.value && sessionStorage.getItem('TipoUsuario') != null) {
            return true;
        } else return false;
    }

    //Para saber si el usuario es interno o externo
    public EsInterno(ParamURL): boolean {

        if ((ParamURL ? this.ParamURL = ParamURL.toLowerCase() : this.ParamURL = 'ext') == "int") { //es interno
            return true;
        } else if (ParamURL == "ext" || ParamURL == undefined || typeof (ParamURL) == 'string' || ParamURL == null) {
            return false
        }
    }

    // Controla los eventos de pegar en un input
    //TODO hacer if más genérico para idcgn IdCnt
    controlPegar(e) {

        let regexStr = '^[0-9]+$';
        if (
            ((e.srcElement.id == "NIT" || e.srcElement.id == "IdCnt" || e.srcElement.id == "idcgn" ) && e.clipboardData.getData('Text').length > 9)
        || ((e.srcElement.id == "password" || e.srcElement.id == "usuario" || e.srcElement.id == "passwordActual" || e.srcElement.id == "password2") && e.clipboardData.getData('Text').length > 30)
        ) { //Si el campo es el NIT o password y la cadena mayor a 9 o 30
            e.stopImmediatePropagation();
            e.preventDefault();
        }

        let regEx = new RegExp(regexStr);
        if (!regEx.test(e.clipboardData.getData('Text')) && e.srcElement.id != 'usuario' ) { // si no cumple con el regex 
            if (e.srcElement.id != 'password' && e.srcElement.id != 'passwordActual' && e.srcElement.id != 'password2') {
                e.stopImmediatePropagation();
                e.preventDefault();
            }
        }
    }

    // Controla los eventos en keydown de un input
    controlKeyDown(e, aux) {

        if (((e.srcElement.id == "NIT" || e.srcElement.id == "nit" || e.srcElement.id == "IdCnt" || e.srcElement.id == "idcgn" || e.srcElement.id == "idCGN") && aux.length >= 9)
        || ((e.srcElement.id == "password" || e.srcElement.id == "usuario" || e.srcElement.id == "passwordActual" || e.srcElement.id == "password2")  && aux.length >= 30)
        ) {

            if (e.key == "Backspace" || e.key == "Tab" || e.code == "ArrowRight" || e.key == "Right" || e.code == "ArrowLeft" || e.key == "Left" || e.code == "Delete" || e.key == "Del" || e.key == "Home" || e.key == "End") return;

            e.stopImmediatePropagation();
            e.preventDefault();
        }
    }
    //Controla los eventos en keyup en un input
    controlKeyUp(e) : any {

        if (e.code == "AltLeft" || e.key == "Alt") {
            e.stopImmediatePropagation();
            e.preventDefault();
            return false;
        }
    }

}