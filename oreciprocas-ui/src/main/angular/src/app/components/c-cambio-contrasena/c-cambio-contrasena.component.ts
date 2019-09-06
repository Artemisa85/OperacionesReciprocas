import { Component, OnInit, Renderer2, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router, RoutesRecognized } from '@angular/router';
import { SCambioContrasenaService } from 'src/app/services/s-cambio-contrasena.service';
import { Utils } from 'src/app/utils/Utils';
import { SRegistroService } from 'src/app/services/s-registro.service';
import { CInicioSesionComponent } from '../c-inicio-sesion/c-inicio-sesion.component';
import { NgxSpinnerService } from 'ngx-spinner';
import { filter, pairwise } from 'rxjs/operators';
import swal from 'sweetalert2';


@Component({
  selector: 'app-c-cambio-contrasena',
  templateUrl: './c-cambio-contrasena.component.html',
  styleUrls: ['./c-cambio-contrasena.component.css']
})
export class CCambioContrasenaComponent implements OnInit {

  // Formularios. Vistas independientes
  OlvidoContrasenaForm: FormGroup;
  PrimeraVezForm: FormGroup;
  CambioContrasenaForm: FormGroup;

  // Banderas de control para decidir qué form activar
  ContrasenaOlvidada: boolean; // Activa OlvidoContrasenaForm
  EsPrimeraVez: boolean; // Activa PrimeraVezForm
  CambioContrasena: boolean; // Activa CambioContrasenaForm

  bloquearCampoNit = false;
  bloquearCampoIdCgn = false;

  ContrIguales: boolean; // Permite saber si las contraseñas ingresadas son iguales
  ErrorDatos: boolean; // Para mostrar mensaje de error
  HabilCambioContr: boolean; // Para permitir acción de botón
  EsUsuarioLogueado: boolean; // Para control de botn

  Habilitar: boolean; // para habilitar el formulario
  DatosEnviados: boolean; // habil cuando los datos han sido enviados

  strCorreo: string; // Almacena el correo consultado
  ParamURL: string; // Parámetro que sirve de filtro para activar c/u de los forms

  // Mensajes para mostrar al volver de enviar datos
  msgErrorDatos: string;
  msgError: string;

  // Controles para formulario
  public UserNIT;
  public idcgn;
  public CorreoE;

  // Control para ir atrás en la navegación de cambioContraseña
  public vistaAnterior;

  @ViewChild('inputCorreo') inputCorreo: ElementRef;
  @ViewChild('inputUserNIT') inputUserNIT: ElementRef;
  @ViewChild('inputIdCGN') inputIdCGN: ElementRef;
  @ViewChild('inputContrasenaActual') inputContrasenaActual: ElementRef;
  @ViewChild('inputContrasena') inputContrasena: ElementRef;
  @ViewChild('inputContrasena2') inputContrasena2: ElementRef;

  constructor(
    private fb: FormBuilder,
    private title: Title,
    private activatedRoute: ActivatedRoute,
    private sContrasena: SCambioContrasenaService,
    private _utils: Utils,
    private sRegistro: SRegistroService,
    private router: Router,
    private cInicioSesion: CInicioSesionComponent,
    private spinner: NgxSpinnerService,
    private renderer2: Renderer2
  ) {

    title.setTitle('Cambio de contraseña');
    this.EsUsuarioLogueado = this._utils.EstaLogueado(); // Se sabe si está logueado
    // Banderas en falso
    this.ContrasenaOlvidada = false;
    this.EsPrimeraVez = false;
    this.CambioContrasena = false;
    this.ContrIguales = false;
    this.HabilCambioContr = false;

    // Si es usuario interno no se muestra el form
    sessionStorage.getItem('TipoUsuario') === 'int' ? this.Habilitar = false : this.Habilitar = true;

  }

  ngOnInit() {
    window.scrollTo(0, 0);
    if (this.router.url === '/CambioContrasena') {
      this.CambioContrasena = true;
      this.buildFormCambio();
      this.CambioContrasenaForm.controls['NIT'].setValue(sessionStorage.getItem('inputNIT'));
      this.CambioContrasenaForm.controls['idcgn'].setValue(sessionStorage.getItem('inputIdCnt'));
      sessionStorage.removeItem('inputNIT');
      sessionStorage.removeItem('inputIdCnt');
      this.CambioContrasenaForm.controls['NIT'].disable();
      this.CambioContrasenaForm.controls['idcgn'].disable();
      return;
    }

    // Obtiene parámetros de la URL
    this.activatedRoute.params.subscribe(params => {
      this.ParamURL = params['param'];
    });

    // Y filtra
    switch (this.ParamURL) {
      // Para construir los diferentes tipos de formulario
      case 'RecuperaContrasena':
        this.ContrasenaOlvidada = true;
        this.buildFormOlvido();
        // Se asignan los controles de OlvidoContrasenaForm para accederlos mediante variables
        this.UserNIT = this.OlvidoContrasenaForm.controls['NIT'];
        this.idcgn = this.OlvidoContrasenaForm.controls['idcgn'];
        this.CorreoE = this.OlvidoContrasenaForm.controls['CorreoE'];
        break;
      case 'ContrasenaPrimeraVez':
        this.EsPrimeraVez = true;
        this.buildFormPrimeraVez();
        break;
      case 'CambiaContrasena':
        this.CambioContrasena = true;
        this.buildFormCambio();
        break;
      default:
        break;
    }

  }

  // Contruye validaciones para form olvidé contraseña
  buildFormOlvido() {
    this.OlvidoContrasenaForm = this.fb.group({
      NIT: ['', Validators.compose([Validators.required, Validators.minLength(9),
        Validators.pattern('^[0-9]+$'), Validators.maxLength(9)])],
      idcgn: ['', Validators.compose([Validators.required,
        Validators.minLength(8), Validators.maxLength(9), Validators.pattern('^[0-9]+$')])],
      CorreoE: ['', Validators.compose([Validators.required, Validators.email])]
    });
  }

  // Construye validaciones para form cambio contraseña por primera vez
  // Debe eliminarse, se decidió que no es necesario
  buildFormPrimeraVez() {
    const currentNit = sessionStorage.getItem('nitEntidad') === undefined ? '' : sessionStorage.getItem('nitEntidad');
    const currentIdCgn = sessionStorage.getItem('idCgnEntidad') === undefined ? '' : sessionStorage.getItem('idCgnEntidad');
    if (currentNit !== '' || currentIdCgn !== '') {
      this.bloquearCampoIdCgn = true;
      this.bloquearCampoNit = true;
    }
    this.PrimeraVezForm = this.fb.group({
      NIT: [currentNit, Validators.compose([Validators.required,
        Validators.minLength(9), Validators.pattern('^[0-9]+$'), Validators.maxLength(9)])],
      idcgn: [currentIdCgn, Validators.compose([Validators.required, Validators.pattern('^[0-9]+$')])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(3)])],
      password2: ['', Validators.compose([Validators.required, Validators.minLength(3)])],
    });
  }

  // Construye validaciones para form cambiar contraseña
  buildFormCambio() {
    const currentNit = sessionStorage.getItem('nitEntidad') === undefined ? '' : sessionStorage.getItem('nitEntidad');
    const currentIdCgn = sessionStorage.getItem('idCgnEntidad') === undefined ? '' : sessionStorage.getItem('idCgnEntidad');
    if (currentNit !== '' || currentIdCgn !== '') {
      this.bloquearCampoIdCgn = true;
      this.bloquearCampoNit = true;
    }

    this.CambioContrasenaForm = this.fb.group({
      NIT: [currentNit, Validators.compose([Validators.required,
        Validators.minLength(9), Validators.pattern('^[0-9]+$'), Validators.maxLength(9)])],
      idcgn: [currentIdCgn, Validators.compose([Validators.required, Validators.pattern('^[0-9]+$')])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(3)])],
      password2: ['', Validators.compose([Validators.required, Validators.minLength(3)])],
      passwordActual: ['', Validators.compose([Validators.required, Validators.minLength(3)])]
    });
  }

  // Consulta el correo
  private ConsultarCorreo(inputUserNIT: string, inputIdCGN) {
    // Valida que existan datos
    if (this.UserNIT.valid && this.idcgn.valid) {
      // Tiene datos, activa spinner
      this.spinner.show();
      // Consulta entidad para extraer correo
      this.sRegistro.ConsultarEntidad(inputUserNIT, inputIdCGN).subscribe(
        (d: any) => {
          this.spinner.hide();
          if (d.correo) { this.CorreoE.setValue(d.correo); }
          this.DatosEnviados = false;
        }, e => {
          this.spinner.hide();
          this.DatosEnviados = true;
          this.ErrorDatos = true;
          this.msgError = 'No existe un correo relacionado';
          this.CorreoE.setValue('');
        }, () => {
        }
      );
    } else {
      // Para que no cause validación
      this.strCorreo = '';
    }
  }

  // Compara las contraseñas
  private ComparaContrasena(inputContrasena: string, inputContrasena2: string): string {
    this.HabilCambioContr = false;
    // Valida que haya datos
    if (inputContrasena === inputContrasena2 && inputContrasena.length > 0) {
      // Coinciden las contraseñas
      this.ContrIguales = true;
      while (this.ParamURL === 'CambiaContrasena' || this.router.url === '/CambioContrasena') {
        // Habilita boton para cambiar si es formulario es válido y coinciden las contraseñas
        this.CambioContrasenaForm.valid ? this.HabilCambioContr = true : this.HabilCambioContr = false;
        return 'coinciden y habilitado';
      }

      return 'Coinciden';
    }

    this.ContrIguales = false;
    return 'No coinciden';
  }

  // Para restablecer contraseña / olvida contraseña
  private RestablecerContrasena(inputUserNIT: string, inputIdCGN: string) {

    this.renderer2.removeClass(this.inputUserNIT.nativeElement, 'campo-invalido');
    this.renderer2.removeClass(this.inputIdCGN.nativeElement, 'campo-invalido');
    this.renderer2.removeClass(this.inputCorreo.nativeElement, 'campo-invalido');

    if (!this.OlvidoContrasenaForm.valid) {
      this.DatosEnviados = true;
      switch (!this.OlvidoContrasenaForm.valid) {
        case !this.OlvidoContrasenaForm.controls['NIT'].valid && !this.OlvidoContrasenaForm.controls['idcgn'].valid
            && !this.OlvidoContrasenaForm.controls['CorreoE'].valid:
          this.ErrorDatos = true;
          this.renderer2.addClass(this.inputUserNIT.nativeElement, 'campo-invalido');
          this.renderer2.addClass(this.inputIdCGN.nativeElement, 'campo-invalido');
          this.renderer2.addClass(this.inputCorreo.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar el NIT, tu identificación asignada por la contaduría y tu correo electrónico';
          break;
        case !this.OlvidoContrasenaForm.controls['NIT'].valid && !this.OlvidoContrasenaForm.controls['idcgn'].valid:
          this.ErrorDatos = true;
          this.renderer2.addClass(this.inputUserNIT.nativeElement, 'campo-invalido');
          this.renderer2.addClass(this.inputIdCGN.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar el NIT y tu identificación asignada por la contaduría';
          break;
        case !this.OlvidoContrasenaForm.controls['NIT'].valid && !this.OlvidoContrasenaForm.controls['CorreoE'].valid:
          this.ErrorDatos = true;
          this.renderer2.addClass(this.inputUserNIT.nativeElement, 'campo-invalido');
          this.renderer2.addClass(this.inputCorreo.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar el NIT y tu correo electrónico';
          break;
        case !this.OlvidoContrasenaForm.controls['idcgn'].valid && !this.OlvidoContrasenaForm.controls['CorreoE'].valid:
          this.ErrorDatos = true;
          this.renderer2.addClass(this.inputIdCGN.nativeElement, 'campo-invalido');
          this.renderer2.addClass(this.inputCorreo.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar tu identificación asignada por la contaduría y tu correo electrónico';
          break;
        case !this.OlvidoContrasenaForm.controls['NIT'].valid:
          this.ErrorDatos = true;
          this.renderer2.addClass(this.inputUserNIT.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar el NIT';
          break;
        case !this.OlvidoContrasenaForm.controls['idcgn'].valid:
          this.ErrorDatos = true;
          this.renderer2.addClass(this.inputIdCGN.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar tu identificación asignada por la contaduría';
          break;
        case !this.OlvidoContrasenaForm.controls['CorreoE'].valid:
          this.ErrorDatos = true;
          this.renderer2.addClass(this.inputCorreo.nativeElement, 'campo-invalido');
          if (this.OlvidoContrasenaForm.get('CorreoE').value != '') {
            this.msgError = 'Correo no válido'
          } else {
            this.msgError = 'Debes ingresar tu correo electrónico';
          }
          break;
      }
    } else {

      this.spinner.show();
      this.sContrasena.RestablecerContrasena(inputUserNIT, inputIdCGN, this.strCorreo).subscribe(
        (data: any) => {
          this.spinner.hide();
          switch (data.status) {
            case 200:
              this.DatosEnviados = true;
              this.ErrorDatos = false;
              this.msgErrorDatos = data.message;
              return;
            case '201':
              this.DatosEnviados = true;
              this.ErrorDatos = false;
              this.msgErrorDatos = data.message;
              return;
            default:
              this.msgError = 'No ha sido posible restablecer su contraseña';
              return;
          }
        }, e => {
          this.spinner.hide();
          console.log(e);
          if (e.status === 0) {
            e.error.message = 'Ha ocurrido un error, por favor intenta nuevamente más tarde.';
          }
          switch (e.status) {
            default:
              // Falso
              this.DatosEnviados = true;
              this.ErrorDatos = true;
              this.msgError = e.error.message;
              return;
          }
        }, () => {
        }
      );
    }
  }
  // Cae en desuso
  // Para establecer contraseña por primera vez
  private PrimeraContrasena(inputContrasena: string) {
    this.spinner.show();

    let NIT: string, IdCGN: string, ContrasenaActual: string;
    // Datos que deben cargarse desde algún modelo
    NIT = '123456789';
    IdCGN = '123456';
    ContrasenaActual = '123';

    this.sContrasena.CambiarContrasena(NIT, IdCGN, ContrasenaActual, inputContrasena, '').subscribe(
      (data: any) => {
        this.spinner.hide();
        switch (data.codigo) {
          case '201':
            this.DatosEnviados = true;
            this.ErrorDatos = false;
            this.msgErrorDatos = data.descripcion;
            return;
          default:
            this.DatosEnviados = true;
            this.ErrorDatos = true;
            this.msgError = 'Código no controlado';
            return;
        }
      }, e => {
        this.spinner.hide();
        switch (e.status) {
          default:
            this.DatosEnviados = true;
            this.ErrorDatos = true;
            this.msgError = 'Error general'
            return;
        }
      }, () => {
        while (this.ErrorDatos !== true) {
          setTimeout(() => {
            this.router.navigate(['/InicioSesion']);
          }, 5000);
          break;
        }
      }
    );
  }

  // Para cambiar contraseña
  private CambiarContrasena(inputUserNIT: string, inputIdCGN: string, inputContrasenaActual: string,
    inputContrasena: string, inputContrasena2: string) {
    this.renderer2.removeClass(this.inputUserNIT.nativeElement, 'campo-invalido');
    this.renderer2.removeClass(this.inputIdCGN.nativeElement, 'campo-invalido');
    this.renderer2.removeClass(this.inputContrasenaActual.nativeElement, 'campo-invalido');
    this.renderer2.removeClass(this.inputContrasena.nativeElement, 'campo-invalido');
    this.renderer2.removeClass(this.inputContrasena2.nativeElement, 'campo-invalido');
    this.DatosEnviados = false; this.ErrorDatos = false;

    if (!this.CambioContrasenaForm.valid) {
      this.DatosEnviados = true;
      let base = 'Debes ingresar '; let contE = 0;
      if (!this.CambioContrasenaForm.controls['NIT'].valid) {
        this.renderer2.addClass(this.inputUserNIT.nativeElement, 'campo-invalido');
        base += 'el NIT'; contE++;
      }
      if (!this.CambioContrasenaForm.controls['idcgn'].valid) {
        this.renderer2.addClass(this.inputIdCGN.nativeElement, 'campo-invalido');
        base += (contE > 0 ? ', ' : '') + 'tu identificación asignada por la contaduría'; contE++;
      }
      if (!this.CambioContrasenaForm.controls['passwordActual'].valid) {
        this.renderer2.addClass(this.inputContrasenaActual.nativeElement, 'campo-invalido');
        base += (contE > 0 ? ', ' : '') + 'tu contraseña actual'; contE++;
      }
      if (!this.CambioContrasenaForm.controls['password'].valid) {
        this.renderer2.addClass(this.inputContrasena.nativeElement, 'campo-invalido');
        base += (contE > 0 ? ', ' : '') + 'tu nueva contraseña'; contE++;
      }
      if (!this.CambioContrasenaForm.controls['password2'].valid) {
        this.renderer2.addClass(this.inputContrasena2.nativeElement, 'campo-invalido');
        base += (contE > 0 ? ' y ' : '') + 'tu confirmación de nueva contraseña'; contE++;
      }
      this.msgError = base;
      this.ErrorDatos = true;
    } else if (inputContrasena !== inputContrasena2) {
      this.DatosEnviados = true;
      this.renderer2.addClass(this.inputContrasena2.nativeElement, 'campo-invalido');
      this.msgError = 'Confirmación de contraseña incorrecta, no coincide con tu nueva contraseña';
      this.ErrorDatos = true;
    } else {
      this.spinner.show();
      this.sContrasena.CambiarContrasena(inputUserNIT, inputIdCGN, inputContrasenaActual, inputContrasena, inputContrasena2).subscribe(
        (data: any) => {
          this.spinner.hide();
          switch (data.status) {
            case 200:
              this.DatosEnviados = true;
              this.ErrorDatos = false;
              this.msgErrorDatos = data.message;
              return;
            default:
              this.DatosEnviados = true;
              this.ErrorDatos = true;
              this.msgErrorDatos = 'código no controlado';
              return;
          }
        }, e => {
          this.spinner.hide();
          switch (e.status) {
            case 400:
              this.DatosEnviados = true;
              this.ErrorDatos = true;
              // this.msgError = e.error.message;
              this.msgError = e.error.errors && e.error.errors.contrasenaNueva && e.error.errors.contrasenaNueva[0]
                ? 'Error en tu nueva contraseña: '
                + e.error.errors.contrasenaNueva[0].message : e.error.message;
              return;
            default:
              this.DatosEnviados = true;
              this.ErrorDatos = true;
              this.msgError = 'Error general';
              return;
          }
        }, () => {
          swal({
            type: 'success',
            title: 'Cambio de contraseña',
            text: 'Tu contraseña ha sido cambiada satisfactoriamente'
          });
          const visAnterior = sessionStorage.getItem('VistaAnterior');
          this.router.navigate([visAnterior]);
        }
      );
    }
  }


  // Manejadores de inputs

  public handlerPegar(e) {
    this._utils.controlPegar(e);
  }

  public handlerKeyDown(e) {
    let aux;
    this.OlvidoContrasenaForm.controls[e.srcElement.id].value ? aux = this.OlvidoContrasenaForm.controls[e.srcElement.id].value : aux = '';
    this._utils.controlKeyDown(e, aux);
  }

  public handlerKeyUp(e) {
    let aux;
    this.OlvidoContrasenaForm.controls[e.srcElement.id].value ? aux = this.OlvidoContrasenaForm.controls[e.srcElement.id].value : aux = '';
    if (this._utils.controlKeyUp(e) === false) {
      setTimeout(() => {
        this.OlvidoContrasenaForm.controls[e.srcElement.id].setValue(aux);
      }, 1);
    }
  }

  // TODO: Estos tres métodos deben ser más genéricos, para utilizar los tres métodos anteriores
  public handlerPegarCambio(e) {
    this._utils.controlPegar(e);
  }

  public handlerKeyDownCambio(e, formData) {
    let aux;
    this.CambioContrasenaForm.controls[e.srcElement.id].value ? aux = this.CambioContrasenaForm.controls[e.srcElement.id].value : aux = '';
    this._utils.controlKeyDown(e, aux);
  }

  public handlerKeyUpCambio(e) {
    let aux;
    this.CambioContrasenaForm.controls[e.srcElement.id].value ? aux = this.CambioContrasenaForm.controls[e.srcElement.id].value : aux = '';
    if (this._utils.controlKeyUp(e) === false) {
      setTimeout(() => {
        this.CambioContrasenaForm.controls[e.srcElement.id].setValue(aux);
      }, 1);
    }
  }

  // Para ir atrás se accede al valor del observable almacenado en sessionStorage
  public IrAtras() {
    sessionStorage.getItem('VistaAnterior') ? this.vistaAnterior = sessionStorage.getItem('VistaAnterior')
      : this.vistaAnterior = '/InicioSesion';
    this.router.navigate([this.vistaAnterior]);
  }

  verPoliticasPSWD() {
    swal({
      type: 'info',
      title: '<strong>Politicas de contraseña</strong>',
      html: '<div class="text-left" style="font-size: medium;">' +
        '<p style="margin:0 0 2px 0">• La longitud de la contraseña debe ser mínimo ocho (8) caracteres.</p>' +
        '<p style="margin:0 0 2px 0">• No se ha usado con anterioridad, al menos en 3 ocasiones.</p>' +
        '<p style="margin:0 0 2px 0">• No contiene su cuenta o nombre completo.</p>' +
        '<p style="margin:0 0 2px 0">• Contiene al menos 3 de los siguientes 4 grupos de caracteres, o debe ser alfanumérica: </p>' +
        '<p style="margin:0 0 0 10px">- Mayúsculas</p>' +
        '<p style="margin:0 0 0 10px">- Minúsculas</p>' +
        '<p style="margin:0 0 0 10px">- Números</p>' +
        '<p style="margin:0 0 0 10px">- Caracteres especiales</p>' +
        '<p style="margin:0 0 0 10px">`~!@#$%^&()_-+={}[]\|:;"\'<>,.?/</p>' +
        '<p style="margin:0 0 2px 0"><b>Otras políticas</b></p>' +
        '<p style="margin:0 0 0 10px"> • Si el usuario no ingresa a la aplicación la clave se inactivará al mes de la creación del usuario.</p>' +
        '<p style="margin:0 0 0 10px">• La contraseña debe cambiarse cada noventa (90) días.</p>' +
        '</div>'
    });
  }
}
