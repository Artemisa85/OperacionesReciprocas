import { Component, OnInit, ViewChild, ElementRef, HostListener, Renderer2 } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SRegistroService } from 'src/app/services/s-registro.service';
import { Utils } from 'src/app/utils/Utils';
import { Entidad } from 'src/app/models/entidad.model';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-c-registro',
  templateUrl: './c-registro.component.html',
  styleUrls: ['./c-registro.component.css']
})
export class CRegistroComponent implements OnInit {
  public pressedButton = '';
  // Control para formulario
  RegistroForm: FormGroup;

  // Almacena datos consultados
  strRazonSocial: string;
  strCorreo: string;
  _Entidad: Entidad;

  // Handler para manejo de info desde sobre creación
  Success: boolean;
  DatosEnviados: boolean;
  msgSuccess: string;
  msgError: string;

  // Controles de RegistroForm
  // Se cargan en ngOnInit para accederlos como variables
  public idcgn;
  public UserNIT;
  public correoUser;
  public razonSocial;

  // División de correo erróneo
  public correoErroneo: boolean;
  public msgErrorCorreo: string;

  @ViewChild('inputCorreo') inputCorreo: ElementRef;
  @ViewChild('inputUserNIT') inputUserNIT: ElementRef;
  @ViewChild('inputIdCGN') inputIdCGN: ElementRef;

  constructor(
    private fb: FormBuilder,
    title: Title,
    private sRegistro: SRegistroService,
    private _utils: Utils,
    private router: Router,
    private spinner: NgxSpinnerService,
    private renderer2: Renderer2
  ) {
    title.setTitle('Registro');
    this.buildForm();
  }

  // Construye el formulario y sus validadores
  // Provee controles para utilizar después

  regExEmail = '^[a-zA-Z0-9._%+-]+@([A-Za-z0-9]+)(([\.\-]?[a-zA-Z0-9]+)*)\\.([A-Za-z]{2,})$';

  buildForm() {
    this.RegistroForm = this.fb.group({

      NIT: ['', Validators.compose([Validators.required, Validators.pattern('^[0-9]+$'),
        Validators.minLength(9), Validators.maxLength(9)])],
      idcgn: ['', Validators.compose([Validators.required, Validators.minLength(8),
        Validators.maxLength(9), Validators.pattern('^[0-9]+$')])],
      correoUser: ['', Validators.compose([Validators.required, Validators.pattern(this.regExEmail)])],
      razonSocial: []

    });
  }

  // Consulta la razón social y el correo
  ConsultarRazonSocialCorreo(inputUserNIT: string, inputIdCGN) {

    // Valida que halla datos en idcgn
    if (this.UserNIT.valid && this.idcgn.valid) {
      this.spinner.show();
      this.sRegistro.ConsultarEntidad(inputUserNIT, inputIdCGN).subscribe(
        (d: any) => {
          this.spinner.hide();
          this._Entidad = d;
          this.strCorreo = this._Entidad.correo;
          this.strRazonSocial = this._Entidad.razonSocial;
          this.RegistroForm.get('correoUser').enable();
          this.correoUser.setValue(this.strCorreo);
          this.razonSocial.setValue(this.strRazonSocial);
          this.DatosEnviados = false;
        }, e => {
          this.spinner.hide();
          this.RegistroForm.get('correoUser').disable();
          this.DatosEnviados = true;
          this.Success = false;
          this.msgError = 'El NIT / usuario ingresado no se encuentra en la base datos de Entidades del FNA';

          this.correoUser.setErrors({
            email: true
          });
          this.correoUser.setValue('');
          this.razonSocial.setValue('');
        }, () => {

        }
      );
    } else {
      // Si no hay datos se dejan vacíos para no generar validación
      this.strRazonSocial = '';
      this.strCorreo = '';
    }
    // }
  }

  ngOnInit() {
    window.scrollTo(0, 0);
    // Carga controles de RegistroForm para accederlos como variables
    this.idcgn = this.RegistroForm.controls['idcgn'];
    this.UserNIT = this.RegistroForm.controls['NIT'];
    this.correoUser = this.RegistroForm.controls['correoUser'];
    this.razonSocial = this.RegistroForm.controls['razonSocial'];

    this.RegistroForm.get('correoUser').disable();
    this.correoUser.setErrors({
      required: true
    });

  }

  // Registra un usuario
  RegistrarUsuario(inputUserNIT: string, inputIdCGN: string, strCorreo: string): any {
    this.renderer2.removeClass(this.inputUserNIT.nativeElement, 'campo-invalido');
    this.renderer2.removeClass(this.inputIdCGN.nativeElement, 'campo-invalido');
    this.renderer2.removeClass(this.inputCorreo.nativeElement, 'campo-invalido');
    if (!this.RegistroForm.valid) {
      this.DatosEnviados = true;
      switch (!this.RegistroForm.valid) {
        case !this.RegistroForm.controls['NIT'].valid && !this.RegistroForm.controls['idcgn'].valid
            && !this.RegistroForm.controls['correoUser'].valid:
          this.Success = false;
          this.renderer2.addClass(this.inputUserNIT.nativeElement, 'campo-invalido');
          this.renderer2.addClass(this.inputIdCGN.nativeElement, 'campo-invalido');
          this.renderer2.addClass(this.inputCorreo.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar el NIT, tu identificación asignada por la contaduría y tu correo electrónico';
          break;
        case !this.RegistroForm.controls['NIT'].valid && !this.RegistroForm.controls['idcgn'].valid:
          this.Success = false;
          this.renderer2.addClass(this.inputUserNIT.nativeElement, 'campo-invalido');
          this.renderer2.addClass(this.inputIdCGN.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar el NIT y tu identificación asignada por la contaduría';
          break;
        case !this.RegistroForm.controls['NIT'].valid && !this.RegistroForm.controls['correoUser'].valid:
          this.Success = false;
          this.renderer2.addClass(this.inputUserNIT.nativeElement, 'campo-invalido');
          this.renderer2.addClass(this.inputCorreo.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar el NIT y tu correo electrónico';
          break;
        case !this.RegistroForm.controls['idcgn'].valid && !this.RegistroForm.controls['correoUser'].valid:
          this.Success = false;
          this.renderer2.addClass(this.inputIdCGN.nativeElement, 'campo-invalido');
          this.renderer2.addClass(this.inputCorreo.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar tu identificación asignada por la contaduría y tu correo electrónico';
          break;
        case !this.RegistroForm.controls['NIT'].valid:
          this.Success = false;
          this.renderer2.addClass(this.inputUserNIT.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar el NIT';
          break;
        case !this.RegistroForm.controls['idcgn'].valid:
          this.Success = false;
          this.renderer2.addClass(this.inputIdCGN.nativeElement, 'campo-invalido');
          this.msgError = 'Debes ingresar tu identificación asignada por la contaduría';
          break;
        case !this.RegistroForm.controls['correoUser'].valid:
          this.Success = false;
          this.renderer2.addClass(this.inputCorreo.nativeElement, 'campo-invalido');
          if (this.RegistroForm.get('correoUser').value !== '') {
            this.msgError = 'Correo no válido';
          } else {
            this.msgError = 'Debes ingresar tu correo electrónico';
          }
          break;
      }
    } else {
      this.spinner.show();
      this.sRegistro.RegistrarUsuario(inputUserNIT, inputIdCGN, strCorreo).subscribe(
        (data: any) => {
          this.spinner.hide();
          this.DatosEnviados = true;
          switch (data.status) {
            case 201:
              this.Success = true;
              this.msgSuccess = data.message;
              return;
            default:
              return;
          }
        }, e => {
          this.spinner.hide();
          this.DatosEnviados = true;
          if (e.status === 0) {
            e.error.message = 'Ha ocurrido un error, por favor intenta nuevamente más tarde.';
          }
          switch (e.status) {
            case 400:
              this.Success = false;
              this.msgError = e.error.message;
              return;
            default:
              this.Success = false;
              this.msgError = 'Datos para registro no válidos, intente de nuevo.'
               + ' Si el problema persiste contacte al administrador del sistema';
              return;
          }
        }, () => {

          while (this.Success === true) {
            setTimeout(() => {
              this.router.navigate(['/InicioSesion']);
            }, 5000);
            break;
          }
        }
      );
    }


  }

  // Valida el formulario || Necesario debido a que ng valida el form en keypress
  // Agrega un error a propósito al formulario para impedir su validez
  ValidarForm() {
    if (!this.correoUser.valid) {
      // Si correoUser no es válido
      this.RegistroForm.setErrors({
        invalid: true
      });
    }
    return;
  }

  // Handlers inputs
  public handlerPegar(e) {
    this._utils.controlPegar(e);
  }

  public handlerKeyDown(e) {
    let aux;
    this.RegistroForm.controls[e.srcElement.id].value ? aux = this.RegistroForm.controls[e.srcElement.id].value : aux = '';
    this._utils.controlKeyDown(e, aux);
  }

  public handlerKeyUp(e) {
    let aux;
    this.RegistroForm.controls[e.srcElement.id].value ? aux = this.RegistroForm.controls[e.srcElement.id].value : aux = '';
    if (this._utils.controlKeyUp(e) === false) {
      setTimeout(() => {
        this.RegistroForm.controls[e.srcElement.id].setValue(aux);
      }, 1);
    }
  }

  // Directiva para capturar keyup
  @HostListener('document:keyup', ['$event'])
  public handleKeyboardEvent(event: KeyboardEvent): void {
    // Funcionalidad removida
  }

  validateEmail(email) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
  }



}



