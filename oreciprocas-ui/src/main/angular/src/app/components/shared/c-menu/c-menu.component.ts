import { Component, OnInit, Injectable, ViewChild, ElementRef, Renderer2, HostListener, AfterContentInit } from '@angular/core';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';
import { SInicioSesionService } from 'src/app/services/s-inicio-sesion.service';
import { Utils } from 'src/app/utils/Utils';
import { CInicioSesionComponent } from '../../c-inicio-sesion/c-inicio-sesion.component';

@Component({
  selector: 'app-c-menu',
  templateUrl: './c-menu.component.html',
  styleUrls: ['./c-menu.component.css', '../../../../assets/libs/bootstrap/css/bootstrap.min.css']
})
@Injectable({
  providedIn: 'root'
})

export class CMenuComponent implements OnInit {
  count = 0;
  EsUsuarioLogueado: boolean;
  username: string;
  userCompan: string;
  ruta: string;
  VisibleAdmin: boolean = false;
  VisibleTransacciones: boolean = false;
  rolUsuario: string;
  public autoridadUser: string;
  ultimoInicioSesion: string;
  public fechaEdicionEntidad;

  @ViewChild("navbarDropdown") navbarDropdown: ElementRef;

  constructor(
    private sInicioSesion: SInicioSesionService
    , private renderer: Renderer2
    , private cInicioSesion: CInicioSesionComponent
    , private router: Router
    , private _SInicioSesionService: SInicioSesionService
  ) {

    // Observa los cambios en sInicioSesion.EsUsuarioLogueado
    this.sInicioSesion.EsUsuarioLogueado.subscribe(value => {
      this.EsUsuarioLogueado = value;
      if (value) {
        setTimeout(() => {
          this.rolUsuario = sessionStorage.getItem('TipoUsuario') === 'ext' ? 'EXTERNAL_USER' : 'FNA_ADMIN';
        }, 0);
      }
    });

    this._SInicioSesionService.fechaEdicion.subscribe(
      value => this.fechaEdicionEntidad = value
    );

    // Observa los cambios en sInicioSesion.username
    this.sInicioSesion.username.subscribe(value => {
      this.username = value;
    });
    // Observa los cambios en sInicioSesion.userCompan
    this.sInicioSesion.userCompan.subscribe(value => {
      this.userCompan = value;
    });
    // Observa los cambios en sInicioSesion.ultimoInicioSesion
    this.sInicioSesion.ultimoInicioSesion.subscribe(value => {
      this.ultimoInicioSesion = value;
    });

  }

  ngOnInit() {
    this._SInicioSesionService.ROL.subscribe(value => this.autoridadUser = value.toString());
    //this.rolUsuario = sessionStorage.getItem('TipoUsuario') === 'ext' ? 'EXTERNAL_USER' : 'FNA_ADMIN';
    //alert(this.rolUsuario);
  }

  ngAfterViewInit() {
    if (this.navbarDropdown !== undefined) {
      this.ajusteRowClass();
    }
  }

  //Cierra sesión
  CerrarSesion() {
    this.sInicioSesion.CerrarSesion();
  }

  //Control de width de la pantalla para ajustar clases cuando el usuario redimensiona
  @HostListener('window:resize', ['$event'])
  onResize(event) {
    if (this.navbarDropdown !== undefined) {
      this.ajusteRowClass();
    }
  }

  ajusteRowClass() {
    let widthWindow = window.innerWidth;

    if (widthWindow < 992) {
      this.renderer.removeClass(this.navbarDropdown.nativeElement, 'row')
    } else {
      this.renderer.addClass(this.navbarDropdown.nativeElement, 'row')
    }
  }

}
