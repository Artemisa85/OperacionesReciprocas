import { Component, OnInit, Injectable, Renderer2, RendererFactory2, ViewChild, ElementRef } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SInicioSesionService } from 'src/app/services/s-inicio-sesion.service';
import { Router, ActivatedRoute, RoutesRecognized, NavigationEnd } from '@angular/router';
import { Utils } from 'src/app/utils/Utils';
import { Entidad } from 'src/app/models/entidad.model';
import { Usuario } from 'src/app/models/usuario.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { filter, pairwise } from 'rxjs/operators';
import { BehaviorSubject } from 'rxjs';
import swal from 'sweetalert2';
import { SEdicionPerfilService } from '../../services/s-edicion-perfil.service';

@Component({
  selector: 'app-c-inicio-sesion',
  templateUrl: './c-inicio-sesion.component.html',
  styleUrls: ['./c-inicio-sesion.component.css']
})
@Injectable({
  providedIn: 'root'
})
export class CInicioSesionComponent implements OnInit {

  // Formularios para int y ext
  private loginFormInt: FormGroup;
  private loginFormExt: FormGroup;
  private ParamURL: string; // URL para identificar el tipo de usuario
  public EsInterno: boolean;
  private ErrorLogin: boolean;
  private _Entidad: Entidad;
  private _Usuario: Usuario;
  private msgError: string;
  private renderer2: Renderer2;
  @ViewChild('inputUsuario') inputUsuario: ElementRef;
  @ViewChild('inputContrasena') inputContrasena: ElementRef;
  @ViewChild('inputNIT') inputNIT: ElementRef;
  @ViewChild('inputIdCnt') inputIdCnt: ElementRef;


  constructor(
    private fb: FormBuilder,
    title: Title,
    private sInicioSesion: SInicioSesionService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private _utils: Utils,
    private spinner: NgxSpinnerService,
    private rendererFactory: RendererFactory2,
    private _SEdicionPerfilService: SEdicionPerfilService
  ) {
    title.setTitle('Iniciar sesión');
    this.renderer2 = rendererFactory.createRenderer(null, null);
    // sessionStorage.clear();
  }

  ngOnInit() {
    window.scrollTo(0, 0);

    // Suscripción a la ruta anterior
    this.router.events
      .pipe(filter((e: any) => e instanceof RoutesRecognized),
        pairwise()
      ).subscribe((e: any) => {
        sessionStorage.setItem('VistaAnterior', e[0].urlAfterRedirects);
      });

    // console.log(this.activatedRoute.snapshot.component.name);
    // this.activatedRoute.
    this.activatedRoute.params.subscribe(params => {
      this.ParamURL = params['param'];
      // this.ParamURL.next(params['param']);
    });

    let path;
    // Control de identificación de distintos tipos de usuario
    this.activatedRoute.url.subscribe(urele => {
      path = urele[0].path;
      if (path === 'InicioSesionFNA') {
        this.EsInterno = true;
        // construye formulario para interno
        this.buildFormInt();
      } else if (path = 'InicioSesion') {
        this.EsInterno = false;
        this.buildFormExt();
      }
    });

  }

  // Construye validaciones de formulario para usuarios internos
  private buildFormInt() {
    this.loginFormInt = this.fb.group({
      usuario: ['', Validators.compose([Validators.required])],
      password: ['', Validators.compose([Validators.required, Validators.maxLength(30)])],
    });
  }

  // Construye validaciones de formulario para usuarios externos
  private buildFormExt() {
    this.loginFormExt = this.fb.group({
      NIT: ['', Validators.compose([Validators.required, Validators.pattern('^[0-9]+$'), Validators.minLength(9), Validators.maxLength(9)])],
      IdCnt: ['', Validators.compose([Validators.required, Validators.pattern('^[0-9]+$'), Validators.minLength(8), Validators.maxLength(9)])],
      password: ['', Validators.compose([Validators.required, Validators.maxLength(30)])],
    });
  }

  // Controla el ingreso de usuarios externos
  protected IngresarExt(inputNIT: string, inputIdCnt: string, inputContrasena: string): any {
    let palabrasClaveExp: string[];

    this.renderer2.removeClass(this.inputNIT.nativeElement, 'campo-invalido');
    this.renderer2.removeClass(this.inputIdCnt.nativeElement, 'campo-invalido');
    this.renderer2.removeClass(this.inputContrasena.nativeElement, 'campo-invalido');
    this.ErrorLogin = false;

    if (!this.loginFormExt.valid) {
      switch (!this.loginFormExt.valid) {
        case !this.loginFormExt.controls['NIT'].valid
          && !this.loginFormExt.controls['IdCnt'].valid
          && !this.loginFormExt.controls['password'].valid:
          this.ErrorLogin = true;
          this.renderer2.addClass(this.inputNIT.nativeElement, 'campo-invalido');
          this.renderer2.addClass(this.inputIdCnt.nativeElement, 'campo-invalido');
          this.renderer2.addClass(this.inputContrasena.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar el NIT, tu identificación asignada por la contaduría y tu contraseña';
          break;
        case !this.loginFormExt.controls['NIT'].valid && !this.loginFormExt.controls['IdCnt'].valid:
          this.ErrorLogin = true;
          this.renderer2.addClass(this.inputNIT.nativeElement, 'campo-invalido');
          this.renderer2.addClass(this.inputIdCnt.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar el NIT y tu Identificación asignada por la contaduría';
          break;
        case !this.loginFormExt.controls['NIT'].valid && !this.loginFormExt.controls['password'].valid:
          this.ErrorLogin = true;
          this.renderer2.addClass(this.inputNIT.nativeElement, 'campo-invalido');
          this.renderer2.addClass(this.inputContrasena.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar el NIT y tu contraseña';
          break;
        case !this.loginFormExt.controls['IdCnt'].valid && !this.loginFormExt.controls['password'].valid:
          this.ErrorLogin = true;
          this.renderer2.addClass(this.inputIdCnt.nativeElement, 'campo-invalido');
          this.renderer2.addClass(this.inputContrasena.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar tu identificación asignada por la contaduría y tu contraseña';
          break;
        case !this.loginFormExt.controls['NIT'].valid:
          this.ErrorLogin = true;
          this.renderer2.addClass(this.inputNIT.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar el NIT';
          break;
        case !this.loginFormExt.controls['IdCnt'].valid:
          this.ErrorLogin = true;
          this.renderer2.addClass(this.inputIdCnt.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar tu identificación asignada por la contaduría';
          break;
        case !this.loginFormExt.controls['password'].valid:
          this.ErrorLogin = true;
          this.renderer2.addClass(this.inputContrasena.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar un valor para tu contraseña';
          break;
        default:
          this.ErrorLogin = true;
          this.msgError = 'Debes ingresar el NIT y la identificación asignada por la contaduría y/o contraseña';
          break;
      }
      return;
    }

    this.spinner.show();
    this.sInicioSesion.ValidarIngresoExt(inputNIT, inputIdCnt, inputContrasena).subscribe(
      (d: any) => {
        this.spinner.hide();
        sessionStorage.setItem('TipoUsuario', 'ext'); // Data que se utiliza como ayuda de identificador dentro del aplicativo
        sessionStorage.setItem('Entidades', d.companies);
        sessionStorage.setItem('nitEntidad', inputNIT);
        sessionStorage.setItem('idCgnEntidad', inputIdCnt);
        sessionStorage.setItem('nombreUsuario', d.name);
        const temporal = d;
        // Debe ser null para que el campo no se cree (no undefined, no vacío)
        this.sInicioSesion.username.next(temporal.name ? temporal.name : null);
        this.sInicioSesion.userCompan.next(temporal.companies ? temporal.companies : 'Sin compañía');
        this._SEdicionPerfilService.getDetalleByIdCgn(inputIdCnt).subscribe(
          response => {
            this.sInicioSesion.fechaEdicion.next(response.result[0].fechaModificacion);
            // El rol con el que se loguea: alert(this.sInicioSesion.ROL.getValue());
            if ( response.result[0].fechaModificacion === null && this.sInicioSesion.ROL.getValue() !== 'ROLE_CGN_USER') {
              // Si es la primera vez que inicia sesión debe ir a editar entidad
              this.router.navigate(['/Perfil']);
              setTimeout(() => {
                swal({
                  type: 'info',
                  title: '<strong>Datos entidad</strong>',
                  text: 'Por favor actualiza los datos de tu entidad.'
                });
              }, 500);
            } else {
              // Si no tiene contraseña vencida, ni es la primera vez que inicia sesión
              this.router.navigate(['/Transacciones']);
            }
          }, error => {
          }
        );

        // this.sInicioSesion.obtenerDatosUsuario(); //se debe hacer es desde el servicio

      }, e => {
        this.msgError = e.error.error_description;
        this.ErrorLogin = true;
        this.spinner.hide();
        palabrasClaveExp = ['expirado', 'vencido', 'caducado'];
        palabrasClaveExp.forEach(el => {
          if (this.msgError.indexOf(el) !== -1) {
            setTimeout(() => {
              this.router.navigate(['/CambioContrasena']);
              sessionStorage.setItem('inputNIT', inputNIT);
              sessionStorage.setItem('inputIdCnt', inputIdCnt);
            }, 2500);
            return;
          }
        });
      }
    );
  }

  // Controla el ingreso de usuarios internos
  protected IngresarInt(inputUsuario: string, inputContrasena: string): any {
    this.renderer2.removeClass(this.inputUsuario.nativeElement, 'campo-invalido');
    this.renderer2.removeClass(this.inputContrasena.nativeElement, 'campo-invalido');
    this.ErrorLogin = false;
    if (!this.loginFormInt.valid) {
      switch (!this.loginFormInt.valid) {
        case !this.loginFormInt.controls['usuario'].valid && !this.loginFormInt.controls['password'].valid:
          this.ErrorLogin = true;
          this.renderer2.addClass(this.inputUsuario.nativeElement, 'campo-invalido');
          this.renderer2.addClass(this.inputContrasena.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar tu usuario y tu contraseña';
          break;
        case !this.loginFormInt.controls['usuario'].valid:
          this.ErrorLogin = true;
          this.renderer2.addClass(this.inputUsuario.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar tu usuario';
          break;
        case !this.loginFormInt.controls['password'].valid:
          this.ErrorLogin = true;
          this.renderer2.addClass(this.inputContrasena.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar tu contraseña';
          break;
        default:
          this.ErrorLogin = true;
          this.msgError = 'Debes ingresar tu usuario y/o tu contraseña';
          break;
      }
      return;
    }

    this.spinner.show();
    this.sInicioSesion.ValidarIngresoInt(inputUsuario, inputContrasena).subscribe(
      (d: any) => {
        if (d) {
          sessionStorage.setItem('TipoUsuario', 'int'); // Data que se utiliza como ayuda de identificador dentro del aplicativo
          sessionStorage.setItem('Entidades', 'No'); // TODO captura entidades y consulta de transacciones por la misma
          sessionStorage.setItem('nombreUsuario', d.name);
          const temporal = d;
          this.sInicioSesion.username.next(temporal.name ? temporal.name : 'Sin nombre');
          this.sInicioSesion.userCompan.next(temporal.companies ? temporal.companies : 'Sin compañía');
          this.router.navigate(['/Transacciones']);
          this.spinner.hide();
        }
      }, e => {
        this.spinner.hide();
        // Datos inválidos...
        this.ErrorLogin = true;
        this.msgError = e.error.error_description;
      }, () => {
      });

  }

  public IraRegistro() {
    this.router.navigate(['/Registro']);
  }

  public IraCambioContrasena() {
    this.router.navigate(['/CambioContrasena/RecuperaContrasena']);
  }

  submit() { }

  // Handlers inputs
  controlPegar(e) {
    this._utils.controlPegar(e);
  }

  controlKeyDown(e) {
    let aux;
    this.loginFormExt.controls[e.srcElement.id].value ? aux = this.loginFormExt.controls[e.srcElement.id].value : aux = '';
    this._utils.controlKeyDown(e, aux);
  }

  controlKeyUp(e) {
    let aux;
    this.loginFormExt.controls[e.srcElement.id].value ? aux = this.loginFormExt.controls[e.srcElement.id].value : aux = '';
    if (this._utils.controlKeyUp(e) === false) {
      setTimeout(() => {
        this.loginFormExt.controls[e.srcElement.id].setValue(aux);
      }, 1);
    }
  }
  // Para internos
  // --> TODO: Hacer más general
  controlKeyDownInt(e) {
    let aux;
    this.loginFormInt.controls[e.srcElement.id].value ? aux = this.loginFormInt.controls[e.srcElement.id].value : aux = '';
    this._utils.controlKeyDown(e, aux);
  }
}
