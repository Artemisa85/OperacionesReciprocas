import { Component, OnInit, Renderer2, ViewChild, ElementRef, HostListener } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { SEdicionPerfilService } from 'src/app/services/s-edicion-perfil.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { SInicioSesionService } from '../../../services/s-inicio-sesion.service';
import { SDivisionPolitica } from '../../../services/s-division-politica';
import swal from 'sweetalert2';
import { DatePipe } from '../../../../../node_modules/@angular/common';
import { Utils } from 'src/app/utils/Utils';
// import { ConsoleReporter } from 'jasmine';

@Component({
  selector: 'app-cperfil',
  templateUrl: './cperfil.component.html',
  styleUrls: ['./cperfil.component.css']
})
export class CPerfilComponent implements OnInit {


  public formFiltroEntidad: FormGroup;
  public FormPerfil: FormGroup;
  public autoridadUser: string;
  public mostrarDepartamentos;
  public valDepartamento = '';
  public mostrarMunicipios;
  public valMunicipio = '';

  // Sección de mensajes al enviar datos al servidor
  // --> Para entidad
  private DatosEnviadosEnt: boolean;
  private ErrorDatosEnt: boolean;
  private msgErrorEnt: string;
  private msgSuccessEnt: string;
  private listaDepartamentos: any;
  private listaMunicipios: any;
  private listaEntidades: any;
  isEntidadDetalle: boolean;
  isCrearEntidad: boolean;
  // --> Para usuario
  private DatosEnviadosUsu: boolean;
  private ErrorDatosUsu: boolean;
  private msgErrorUsu: string;
  private msgSuccessUsu: string;
  public rangoPag: number;
  public numPaginaActual: number; // Variable para almacenar la página actual
  public ultimaPag: number; // Para almacenar la última página
  private nuEntidadesxVista: number;
  private msgError: string;
  private errorDatos: boolean;

  @ViewChild('inputNit') inputNit: ElementRef;
  @ViewChild('inputIdCgn') inputIdCgn: ElementRef;
  @ViewChild('inputNombreEntidad') inputNombreEntidad: ElementRef;
  @ViewChild('inputSigla') inputSigla: ElementRef;
  @ViewChild('inputDpto') inputDpto: ElementRef;
  @ViewChild('inputCiudad') inputCiudad: ElementRef;
  @ViewChild('inputDireccion') inputDireccion: ElementRef;
  @ViewChild('inputTelefono') inputTelefono: ElementRef;
  @ViewChild('inputNombreContacto') inputNombreContacto: ElementRef;
  @ViewChild('inputApellidoContacto') inputApellidoContacto: ElementRef;
  @ViewChild('inputSitioWeb') inputSitioWeb: ElementRef;
  @ViewChild('inputCorreo') inputCorreo: ElementRef;


  constructor(
    private fb: FormBuilder
    , private _NgxSpinnerService: NgxSpinnerService
    , private _SEdicionPerfilService: SEdicionPerfilService
    , private _SInicioSesionService: SInicioSesionService
    , private _SDivisionPolitica: SDivisionPolitica
    , private renderer2: Renderer2
    , private _utils: Utils
  ) {
    this.mostrarDepartamentos = 'none';
    this.mostrarMunicipios = 'none';
  }

  ngOnInit() {
    this._SInicioSesionService.ROL.subscribe(value => this.autoridadUser = value.toString());
    let idCgnExternal;
    this._SInicioSesionService.idCgnLogin.subscribe(value => idCgnExternal = value.toString());
    window.scrollTo(0, 0);
    this.buildFormFiltro();
    this.buildFormPerfil();
    this.isCrearEntidad = false;
    if (this.autoridadUser === 'ROLE_EXTERNAL_USER') {
      this.obtenerDetalleEntidad(idCgnExternal);
      this.isEntidadDetalle = true;
    } else if (this.autoridadUser === 'ROLE_FNA_ADMIN') {
      this.nuEntidadesxVista = 5;
      this.obtenerEntidades();
      this.isEntidadDetalle = false;
    }
    this.errorDatos = false;

  }

  // Formulario busqueda
  buildFormFiltro() {
    this.formFiltroEntidad = this.fb.group({
      filtroNit: [''],
      filtroIdCgn: [''],
      filtroNombreEntidad: ['']
    });
  }

  // Construcción del formulario de edicion de entidad
  buildFormPerfil() {
    this.FormPerfil = this.fb.group({
      nit: ['', Validators.compose([Validators.required, Validators.minLength(9)])],
      idCGN: ['', Validators.compose([Validators.required, Validators.minLength(8)])],
      fechaModificacion: ['', /*Validators.compose([Validators.required])*/],
      razonSocial: ['', Validators.compose([Validators.required, Validators.maxLength(200)])],
      sigla: ['', Validators.compose([Validators.maxLength(50)])],
      codigoDepartamento: ['', Validators.compose([Validators.required])],
      codigoMunicipio: ['', Validators.compose([Validators.required])],
      direccion: ['', Validators.compose([Validators.required, Validators.maxLength(100)])],
      telefono: ['', Validators.compose([Validators.required, Validators.maxLength(50), Validators.minLength(5),
      /*Validators.pattern('^[0-9]+$')*/])],
      nombreContacto: ['', Validators.compose([Validators.required, Validators.maxLength(40)])],
      apellidoContacto: ['', Validators.compose([Validators.required, Validators.maxLength(40)])],
      sitioWEB: ['', Validators.compose([Validators.maxLength(100)])],
      correo: ['', Validators.compose([Validators.required, Validators.maxLength(100),
      Validators.pattern(/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/), Validators.email])],
      activo: true
    });
  }

  // Va a editar el perfil
  editarPerf(form) {
    this.renderer2.removeClass(this.inputNit.nativeElement, 'campo-invalido');
    this.renderer2.removeClass(this.inputIdCgn.nativeElement, 'campo-invalido');
    this.renderer2.removeClass(this.inputNombreEntidad.nativeElement, 'campo-invalido');
    this.renderer2.removeClass(this.inputSigla.nativeElement, 'campo-invalido');
    this.renderer2.removeClass(this.inputDpto.nativeElement, 'campo-invalido-dropdown');
    this.renderer2.removeClass(this.inputCiudad.nativeElement, 'campo-invalido-dropdown');
    this.renderer2.removeClass(this.inputDireccion.nativeElement, 'campo-invalido');
    this.renderer2.removeClass(this.inputTelefono.nativeElement, 'campo-invalido');
    this.renderer2.removeClass(this.inputNombreContacto.nativeElement, 'campo-invalido');
    this.renderer2.removeClass(this.inputApellidoContacto.nativeElement, 'campo-invalido');
    this.renderer2.removeClass(this.inputSitioWeb.nativeElement, 'campo-invalido');
    this.renderer2.removeClass(this.inputCorreo.nativeElement, 'campo-invalido');
    this.errorDatos = false;
    if (!this.FormPerfil.valid) {
      let base = 'Debes ingresar '; let contE = 0;

      if (!this.FormPerfil.controls['nit'].valid) {
        this.renderer2.addClass(this.inputNit.nativeElement, 'campo-invalido');
        base += 'el NIT de la entidad'; contE++;
      }

      if (!this.FormPerfil.controls['idCGN'].valid) {
        this.renderer2.addClass(this.inputIdCgn.nativeElement, 'campo-invalido');
        base += (contE > 0 ? ', ' : '') + 'el identificador asignado por la CGN'; contE++;
      }

      if (!this.FormPerfil.controls['razonSocial'].valid) {
        this.renderer2.addClass(this.inputNombreEntidad.nativeElement, 'campo-invalido');
        base += (contE > 0 ? ', ' : '') + 'el nombre de la entidad'; contE++;
      }
      if (!this.FormPerfil.controls['sigla'].valid) {
        this.renderer2.addClass(this.inputSigla.nativeElement, 'campo-invalido');
        base += (contE > 0 ? ', ' : '') + 'tu sigla'; contE++;
      }
      if (!this.FormPerfil.controls['codigoDepartamento'].valid) {
        this.renderer2.addClass(this.inputDpto.nativeElement, 'campo-invalido-dropdown');
        base += (contE > 0 ? ', ' : '') + 'tu departamento'; contE++;
      }
      if (!this.FormPerfil.controls['codigoMunicipio'].valid) {
        this.renderer2.addClass(this.inputCiudad.nativeElement, 'campo-invalido-dropdown');
        base += (contE > 0 ? ', ' : '') + 'tu ciudad'; contE++;
      }
      if (!this.FormPerfil.controls['direccion'].valid) {
        this.renderer2.addClass(this.inputDireccion.nativeElement, 'campo-invalido');
        base += (contE > 0 ? ', ' : '') + 'tu dirección'; contE++;
      }
      if (!this.FormPerfil.controls['telefono'].valid) {
        this.renderer2.addClass(this.inputTelefono.nativeElement, 'campo-invalido');
        base += (contE > 0 ? ', ' : '') + 'tu teléfono'; contE++;
      }
      if (!this.FormPerfil.controls['nombreContacto'].valid) {
        this.renderer2.addClass(this.inputNombreContacto.nativeElement, 'campo-invalido');
        base += (contE > 0 ? ', ' : '') + 'tu nombre de contacto'; contE++;
      }
      if (!this.FormPerfil.controls['apellidoContacto'].valid) {
        this.renderer2.addClass(this.inputApellidoContacto.nativeElement, 'campo-invalido');
        base += (contE > 0 ? ', ' : '') + 'tu apellido de contacto'; contE++;
      }
      if (!this.FormPerfil.controls['sitioWEB'].valid) {
        this.renderer2.addClass(this.inputSitioWeb.nativeElement, 'campo-invalido');
        base += (contE > 0 ? ', ' : '') + 'tu sitio Web'; contE++;
      }
      if (!this.FormPerfil.controls['correo'].valid) {
        this.renderer2.addClass(this.inputCorreo.nativeElement, 'campo-invalido');
        base += (contE > 0 ? ' y ' : '') + 'un correo válido'; contE++;
      }
      this.msgError = base;
      this.errorDatos = true;
    } else {

      // console.log(form);
      this._NgxSpinnerService.show();

      if (this.isCrearEntidad) {
        this._SEdicionPerfilService.crearEntidad(form).subscribe(
          response => {
            swal({
              type: 'success',
              title: 'Entidad creada',
              text: 'La Entidad ha sido creada satisfactoriamente'
            });
            this.obtenerDetalleEntidad(form.idCGN);
            this._NgxSpinnerService.hide();
          }, error => {
            if (error.status === 0) {
              error.error.message = 'Ha ocurrido un error, por favor intenta nuevamente más tarde.';
            }
            swal({
              type: 'error',
              title: 'Error',
              text: 'No fue posible crear la entidad ' + error.error.message
            });
            this._NgxSpinnerService.hide();
          }
        );
        this.restablecerFormEdit();
      } else {
        this._SEdicionPerfilService.actualizarDatos(form).subscribe(
          response => {
            swal({
              type: 'success',
              title: 'Entidad actualizada',
              text: 'La Entidad ha sido actualizada satisfactoriamente'
            });
            this.obtenerDetalleEntidad(form.idCGN);
            this._NgxSpinnerService.hide();
          }, error => {
            if (error.status === 0) {
              error.error.message = 'Ha ocurrido un error, por favor intenta nuevamente más tarde.';
            }
            swal({
              type: 'error',
              title: 'Error',
              text: 'No fue posible actualizar la entidad ' + error.error.message
            });
            this._NgxSpinnerService.hide();
          }
        );
        this.restablecerFormEdit();
      }

    }
  }

  // Para formatear la fecha
  formatFecha(fecha) {
    if (fecha) {
      let dd = fecha.getDate();
      let mm = fecha.getMonth() + 1; // January is 0!

      const yyyy = fecha.getFullYear();
      if (dd < 10) {
        dd = '0' + dd;
      }
      if (mm < 10) {
        mm = '0' + mm;
      }
      fecha = dd + '/' + mm + '/' + yyyy;
    } else {
      fecha = ' ';
    }
    return fecha;
  }


  updateListMunicipios(newDtpo) {
    this.FormPerfil.controls['codigoDepartamento'].setValue(newDtpo.codigoDANE);
    this.valDepartamento = newDtpo.nombre;
    this.mostrarDepartamentos = 'none';
    this._SDivisionPolitica.getMunicipiosXDepartamento(newDtpo.codigoDANE).subscribe(
      response2 => {
        this.listaMunicipios = response2.result[0].municipiosColl;
        const existCurrentMun = this.listaMunicipios.find(x => x.codigoDANE === this.FormPerfil.controls['codigoMunicipio'].value);
        if (existCurrentMun) {
          this.valMunicipio = existCurrentMun.nombre;
        } else {
          this.valMunicipio = '';
          this.FormPerfil.controls['codigoMunicipio'].setValue('');
        }
      },
      error => {
        this.listaMunicipios = [];
      }
    );
  }

  obtenerDetalleEntidad(idCgn) {
    this.valDepartamento = '';
    this.valMunicipio = '';
    this.errorDatos = false;

    this._NgxSpinnerService.show();
    this.isEntidadDetalle = true;

    this._SEdicionPerfilService.getDetalleByIdCgn(idCgn).subscribe(
      response => {
        setTimeout(() => {
          const entidadDetalle = response.result[0];
          this.FormPerfil.controls['nit'].setValue(entidadDetalle.nit.trim());
          this.FormPerfil.controls['idCGN'].setValue(entidadDetalle.idCGN.trim());
          const fechaPipe = new DatePipe('en-US');
          this._SInicioSesionService.fechaEdicion.next(entidadDetalle.fechaModificacion);
          // sessionStorage.setItem('fechaEdicion', entidadDetalle.fechaModificacion);
          this.FormPerfil.controls['fechaModificacion'].setValue(fechaPipe.transform(entidadDetalle.fechaModificacion,
            'dd-MM-yyyy HH:mm:ss'));
          this.FormPerfil.controls['razonSocial'].setValue(entidadDetalle.razonSocial.trim());
          this.FormPerfil.controls['sigla'].setValue(entidadDetalle.sigla.trim());

          this._SDivisionPolitica.getAllDepartamentos().subscribe(
            response2 => {
              this.listaDepartamentos = response2.result[0].departamentosPag.content;
              // this.FormPerfil.controls['codigoDepartamento'].setValue(entidadDetalle.codigoDepartamento.trim());
              this.updateListMunicipios(this.listaDepartamentos.find(x => x.codigoDANE === entidadDetalle.codigoDepartamento));
              this.FormPerfil.controls['codigoMunicipio'].setValue(entidadDetalle.codigoMunicipio.trim());
            },
            error => {
              this.listaDepartamentos = [];
            }
          );
          this.FormPerfil.controls['direccion'].setValue(entidadDetalle.direccion.trim());
          this.FormPerfil.controls['telefono'].setValue(entidadDetalle.telefono.trim());
          this.FormPerfil.controls['nombreContacto'].setValue(entidadDetalle.nombreContacto.trim());
          this.FormPerfil.controls['apellidoContacto'].setValue(entidadDetalle.apellidoContacto.trim());
          this.FormPerfil.controls['sitioWEB'].setValue(entidadDetalle.sitioWEB.trim());
          this.FormPerfil.controls['correo'].setValue(entidadDetalle.correo.trim());
        }, 0);
        this._NgxSpinnerService.hide();
      }, e => {
        this._NgxSpinnerService.hide();
      }
    );
  }

  obtenerEntidades() {
    const cuerpo = {
      nit: this.formFiltroEntidad.controls['filtroNit'].value,
      idCgn: this.formFiltroEntidad.controls['filtroIdCgn'].value,
      razonSocial: this.formFiltroEntidad.controls['filtroNombreEntidad'].value
    };
    this._NgxSpinnerService.show();
    this._SEdicionPerfilService.getEntidades(this.nuEntidadesxVista, '0', cuerpo).subscribe(
      response => {
        this.listaEntidades = response.result[0].entidades.content;
        this.ultimaPag = response.result[0].entidades.totalPages; // Obtiene el total de páginas
        this.numPaginaActual = response.result[0].entidades.number; // Obtiene la página actual
        this.rangoPag = response.result[0].entidades.number;
        this._NgxSpinnerService.hide();
      },
      error => {
        if (error.status === 0) {
          error.error.message = 'Ha ocurrido un error, por favor intenta nuevamente más tarde.';
        }
        swal({
          type: 'error',
          title: 'Error',
          text: 'No fue posible cargar la lista de entidades ' + error.error.message
        });
        this._NgxSpinnerService.hide();
      }
    );
  }

  // Método para paginar. El parámetro se obtiene desde la vista
  Paginar(param: string): void {
    this._NgxSpinnerService.show();
    let paginaDestino;
    // Procedimiento para obtener el valor de la página de destino
    switch (param) {
      case 'first':
        paginaDestino = 0;
        this.rangoPag = paginaDestino;
        break;
      case 'last':
        paginaDestino = this.ultimaPag - 1;
        this.rangoPag = Math.floor(paginaDestino / 4) * 4;
        break;
      case '+4':
        paginaDestino = this.numPaginaActual + 4;
        if ((this.numPaginaActual + 4 > this.ultimaPag - 1)) {
          paginaDestino = this.numPaginaActual;
        } else {
          this.rangoPag = Math.floor(paginaDestino / 4) * 4;
        }
        break;
      case '-4':
        paginaDestino = this.numPaginaActual - 4;
        if ((this.numPaginaActual - 4 > this.ultimaPag - 1)) {
          paginaDestino = this.numPaginaActual;
        } else {
          this.rangoPag = Math.floor(paginaDestino / 4) * 4;
        }
        break;
      case '+1':
        paginaDestino = this.numPaginaActual + 1;
        if ((this.numPaginaActual + 1 > this.ultimaPag - 1)) {
          paginaDestino = this.numPaginaActual;
        } else if (paginaDestino % 4 === 0) {
          this.rangoPag = paginaDestino;
        }
        break;
      case '-1':
        paginaDestino = this.numPaginaActual - 1;
        if (this.numPaginaActual - 1 < 0) {
          paginaDestino = this.numPaginaActual;
        } else if (this.numPaginaActual % 4 === 0) {
          this.rangoPag = this.numPaginaActual - 4;
        }
        break;
      default:
        paginaDestino = parseInt(param, 10);
        if ((paginaDestino + 1) % 4 === 0) {
          this.rangoPag = paginaDestino - 3;
        }
        break;
    }
    const cuerpo = {
      nit: this.formFiltroEntidad.controls['filtroNit'].value,
      idCgn: this.formFiltroEntidad.controls['filtroIdCgn'].value,
      razonSocial: this.formFiltroEntidad.controls['filtroNombreEntidad'].value
    };
    this._SEdicionPerfilService.getEntidades(this.nuEntidadesxVista.toString(), paginaDestino, cuerpo).subscribe(
      response => {
        this._NgxSpinnerService.hide();
        this.listaEntidades = response.result[0].entidades.content;
        this.numPaginaActual = response.result[0].entidades.number; // Obtiene número de pagina actual
        // sessionStorage.setItem('TablaEntidades', JSON.stringify(this.listaEntidades)); // Actualiza tabla de transacciones en session
        // sessionStorage.setItem('numPaginaActual', this.numPaginaActual.toString()); // Guarda número de la página actual
      }, e => {
        this._NgxSpinnerService.hide();
        console.error('Error consultando las entidades: ', e);
      }, () => {
      }
    );
  }

  activarFormularioCreacion() {
    this.valDepartamento = '';
    this.valMunicipio = '';
    this.errorDatos = false;
    this.isCrearEntidad = true;
    this._SDivisionPolitica.getAllDepartamentos().subscribe(
      response => {
        this.listaDepartamentos = response.result[0].departamentosPag.content;
      },
      error => {
        this.listaDepartamentos = [];
      });
  }

  restablecerFormEdit() {
    this.isEntidadDetalle = false;
    this.isCrearEntidad = false;
    this.buildFormPerfil();
  }


  // Handlers inputs
  controlPegar(e) {
    this._utils.controlPegar(e);
  }

  controlKeyDown(e) {
    let aux;
    this.FormPerfil.controls[e.srcElement.id].value ? aux = this.FormPerfil.controls[e.srcElement.id].value : aux = '';
    this._utils.controlKeyDown(e, aux);
  }

  controlKeyDownInt(e) {
    let aux;
    this.FormPerfil.controls[e.srcElement.id].value ? aux = this.FormPerfil.controls[e.srcElement.id].value : aux = '';
    this._utils.controlKeyDown(e, aux);
  }

  showDepartamentos() {
    this.mostrarDepartamentos = this.mostrarDepartamentos === 'block' ? 'none' : 'block';
  }

  changeValMunicipio(municipio) {
    this.FormPerfil.controls['codigoMunicipio'].setValue(municipio.codigoDANE);
    this.valMunicipio = municipio.nombre;
    this.mostrarMunicipios = 'none';
  }

  showMunicipios() {
    this.mostrarMunicipios = this.mostrarMunicipios === 'block' ? 'none' : 'block';
  }

  hideDepartamentos() {
    this.mostrarDepartamentos = 'none';
  }

  hideMunicipios() {
    this.mostrarMunicipios = 'none';
  }

  public validNumber(value) {
    const numero = /^([0-9])*$/;
    return numero.test(value);
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) {
    const top4 = document.getElementById('start4');
    const top5 = document.getElementById('start5');
    let list = null;
    let current = null;
    if (top4 === document.activeElement) {
      list = document.getElementById('list4');
      current = top4;
    } else if (top5 === document.activeElement) {
      list = document.getElementById('list5');
      current = top5;
    }
    // tslint:disable-next-line:no-shadowed-variable
    let first = null;
    let last = null;
    if (list) {
      first = list.firstChild.nextSibling;
      last = list.lastChild;
    }

    switch (event.keyCode) {
      case 38: // if the UP key is pressed
        if (document.activeElement === current) {
          last.focus();
        } else {
          let before = null;
          before = document.activeElement.previousSibling;
          before.focus();
        }
        break;
      case 40: // if the DOWN key is pressed
        if (document.activeElement === current) {
          first.focus();
        } else {
          let next = null;
          next = document.activeElement.nextSibling;
          next.focus();
        }
        break;
    }
  }

  hideAll() {
    this.hideDepartamentos();
    this.hideMunicipios();
  }

}
