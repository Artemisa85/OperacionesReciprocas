import { Component, OnInit} from '@angular/core';
// Para el manejo de peticiones http a rest
import { HttpClient } from '@angular/common/http';
// Manejo de rutas
import {  RouterOutlet } from '@angular/router';;
// Spinner - loader
import { NgxSpinnerService } from 'ngx-spinner';
// Animaciones enter vistas
import { slideInAnimation } from './utils/animations';
import { fromEvent } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css',  '../assets/libs/bootstrap/css/bootstrap.min.css'],
  animations: [
    slideInAnimation
  ]
})
export class AppComponent implements OnInit {

  //Para el primer ingreso
  display: string;

  //Captura de eventos para establecer la actividad del usuario
  public clicks$ = fromEvent(document, 'click');
  public scroll$ = fromEvent(document, 'scroll');
  public keydown$ = fromEvent(document, "keydown");
  public mousemove$ = fromEvent(document, "mousemove");

  constructor(
    http: HttpClient
    , private spinner: NgxSpinnerService
  ) {
    this.display = 'block'; //Para el spinner inicial
  }

  ngOnInit() {
    /* spinner inicial */
    this.spinner.show();
    setTimeout(() => {
      this.spinner.hide();
      this.display = 'none'; //Así solo se instancia uno de los dos
    }, 1500);

  }

  ngAfterViewInit() {

  }

  // Envía datos del router para las animaciones al cambiar de vistas
  prepareRoute(outlet: RouterOutlet) {
    return outlet && outlet.activatedRouteData && outlet.activatedRouteData['animation'];
  }

  // Scroll para subir
  public IrArriba() {
    let scrollToTop = window.setInterval(() => {
      let pos = window.pageYOffset;
      if (pos > 0) {
        window.scrollTo(0, pos - 90); // cantidad de espacio en y que recorrerá el scroll
      } else {
        window.clearInterval(scrollToTop);
      }
    }, 16); //cada cuánto subirá
  }

}