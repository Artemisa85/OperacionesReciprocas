import { Component, AfterViewInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter, distinctUntilChanged, map } from 'rxjs/operators';
import { SInicioSesionService } from 'src/app/services/s-inicio-sesion.service';

@Component({
  selector: 'app-c-miga-pan',
  templateUrl: './c-miga-pan.component.html',
  styleUrls: ['./c-miga-pan.component.css']
})

export class CMigaPanComponent implements AfterViewInit {

  private EsUsuarioLogueado: boolean;
  display: string;

  breadcrumbs$ = this.router.events.pipe(
    filter(event => event instanceof NavigationEnd)
    , distinctUntilChanged()
    , map(event => this.ConstruirBreadCrumb(this.activatedRoute.root))
  );

  constructor(private activatedRoute: ActivatedRoute, private router: Router,
    private sInicioSesion: SInicioSesionService) {
    this.sInicioSesion.EsUsuarioLogueado.subscribe(value => {
      this.EsUsuarioLogueado = value;
    });
  }

  // Construye la ruta del breadCrumb
  ConstruirBreadCrumb(route: ActivatedRoute, url: string = '', MigasPan: Array<MigaPan> = []): any {
    // Define si se muestra, dependiendo de si es o no usuario logueado
    if (!this.EsUsuarioLogueado) {
      this.display = 'none';
    } else {
      this.display = 'block';
    }
    // Si no hay una ruta disponible, entonces estamos en la raíz
    const label = route.routeConfig ? route.routeConfig.data['breadcrumb'] : 'Inicio›';
    const path = route.routeConfig ? route.routeConfig.path : '';
    // En routeConfig la ruta completa no está disponible,
    // entonces se reconstruye cada vez
    const nextUrl = `${url}${path}`;
    const breadcrumb = {
      label: label,
      url: nextUrl,
    };
    const nuevaMiga = [...MigasPan, breadcrumb];
    if (route.firstChild) {
      return this.ConstruirBreadCrumb(route.firstChild, nextUrl, nuevaMiga);
    }
    return nuevaMiga;
  }

  ngAfterViewInit(): void {
    // TODO
  }


}

// Interfaz para el componente de  miga de pan
export interface MigaPan {
  label: string;
  url: string;
}

