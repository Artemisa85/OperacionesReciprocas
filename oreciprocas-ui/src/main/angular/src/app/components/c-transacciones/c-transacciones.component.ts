import { Component, OnInit, ViewChild, ElementRef, Renderer2, HostListener, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CMenuComponent } from '../shared/c-menu/c-menu.component';
import { NgxSpinnerService } from 'ngx-spinner';
import { SOperacionesReciprocasService } from 'src/app/services/s-operaciones-reciprocas.service';
import { SInicioSesionService } from 'src/app/services/s-inicio-sesion.service';
import { SDescargarTransaccionesService } from '../../services/s-descargar-transacciones.service';
import * as FileSaver from 'file-saver';
import swal from 'sweetalert2';
import { TypeaheadComponent } from '../typeahead/typeahead.component';

@Component({
  selector: 'app-c-transacciones',
  templateUrl: './c-transacciones.component.html',
  styleUrls: ['./c-transacciones.component.css'],
})
export class CTransaccionesComponent implements OnInit, AfterViewInit {

  @ViewChild(TypeaheadComponent) typeaheadComponent: TypeaheadComponent;

  ModalForm: FormGroup; // Grupo ventana modal
  display: string; // Permite mostrar la ventana modal
  FormFiltrosPrim: FormGroup; // Primer Grupo búsqueda de transacciones
  FormFiltrosSeg: FormGroup; // Segundo Grupo búsqueda de transacciones
  public autoridadUser: string;
  public rangoPag: number;
  public numPaginaActual: number; // Variable para almacenar la página actual
  public ultimaPag: number; // Para almacenar la última página
  private numTransaccionesxVista: number; // Para almacenar el número de transacciones descargadas por vista
  private bodyFiltros: any; // Para almacenar los valores de los filtros de búsqueda.
  public mostrarEstados;
  public mostrarPeriodos;
  public mostrarAnnios;

  @ViewChild('tableTabla') tableTabla: ElementRef;
  @ViewChild('slctEntidadesID') slctEntidadesID: ElementRef;
  @ViewChild('divPrimFilt') divPrimFilt: ElementRef;
  @ViewChild('inpFiltFecha') inpFiltFecha: ElementRef;
  public entidades: any;

  public transacciones; // Guarda tabla de transacciones

  // Variables para almacenar data de filtros
  public datosMultSlc; // persisten datos de filtro: FechaConsolidacion
  public auxdatosMultSlc; // lista que guarda data filtrada para el filtro Fecha
  public optVisible; // Define si mostrar o no el campo de select multiple para el filtro de fecha
  public periodos;
  public annios;
  public estados;
  // Almacena filtros seleccionados
  public valPeriodo = '';
  public valPeriodoObject;
  public valAnnio = '';
  public valFecha;
  public valEstado = '';
  public valEstadoObject;

  constructor(
    private router: Router
    , private fb: FormBuilder
    , private renderer: Renderer2
    , private spinner: NgxSpinnerService
    , private _SOperacionesReciprocasService: SOperacionesReciprocasService
    , private _SDescargaTransaccionesService: SDescargarTransaccionesService
    , private _SInicioSesionService: SInicioSesionService
  ) {
    this.mostrarEstados = 'none';
    this.mostrarPeriodos = 'none';
    this.mostrarAnnios = 'none';
  }

  // Validaciones
  private buildFormModal() {
    this.ModalForm = this.fb.group({
      slctEntidades: ['', Validators.compose([Validators.required])] // Solo es requerido
    });
  }
  // Construcción de formulario
  private buildFormFiltrosPrim() {
    this.FormFiltrosPrim = this.fb.group({
      periodo: ['', Validators.compose([])],
      fechaConsolidacion: ['', Validators.compose([])],
      annio: ['', Validators.compose([])],
      codEstado: ['', Validators.compose([])],
      entidadesInv: ['', Validators.compose([])],
      idTransaccion: ['', Validators.compose([])],
      idEntidad: ['', Validators.compose([])]
    });
  }
  // Método para ventana modal (param: entidad elegida)
  SeleccionarEntidad(slctEntidades) {
    // Valida que el parámetro posea algún valor
    if (slctEntidades.value !== undefined) {
      sessionStorage.setItem('EntidadUsuario', slctEntidades.value);
      this.cargarOperaciones(slctEntidades.value);
      this.display = 'none';
    }
  }

  // Carga operaciones recíprocas dependiendo de la selección del select
  cargarOperaciones(idEntidad) {
    const idUsuario = '1';
    this.spinner.show();
    this._SOperacionesReciprocasService.ConsultarTransacciones(idUsuario, idEntidad, '').subscribe(
      response => {
        this.spinner.hide();
        console.log(response);
      }, error => {
        this.spinner.hide();
        console.error(error);
      }, () => {
        console.log('successCargaOP');
      }
    );
    this.spinner.hide();
  }

  ngOnInit() {
    window.scrollTo(0, 0);
    sessionStorage.removeItem('idOpRecip');
    this._SInicioSesionService.ROL.subscribe(value => this.autoridadUser = value.toString());
    this.cargarFiltros();

    this.numTransaccionesxVista = 5; // Se establece el número de transacciones por vista
    this.optVisible = false; // Por default, el espacio de selección para fecha de consolidación no debe ser visible
    this.entidades = sessionStorage.getItem('Entidades'); // Obtiene las entidades del sessionStorage

    typeof (this.entidades) === 'string' ? sessionStorage.setItem('EntidadUsuario', this.entidades) : this.entidades = 'null';

    // Decide si mostrar el modal
    // Verifica que ya se haya elegido una entidad
    if (sessionStorage.getItem('EntidadUsuario') !== null) {
      this.display = 'none';
    } else {
      this.display = 'block';
    }

    // Se construyen ambos formularios
    this.buildFormModal();
    this.buildFormFiltrosPrim();

    // Si los siguientes valores en sessionStorage son diferentes de nulo, los carga
    // (Toman el valor después de la primera carga de transacciones)
    if (sessionStorage.getItem('TablaTransacciones') !== null) {
      this.transacciones = JSON.parse(sessionStorage.getItem('TablaTransacciones'));
    }

    if (sessionStorage.getItem('numPaginaActual') !== null) {
      this.numPaginaActual = parseInt(sessionStorage.getItem('numPaginaActual'), 10);
    }

    if (sessionStorage.getItem('Filtros') !== null) {
      this.bodyFiltros = sessionStorage.getItem('Filtros');
    }

    if (sessionStorage.getItem('numUltimaPagina') !== null) {
      this.ultimaPag = parseInt(sessionStorage.getItem('numUltimaPagina'), 10);
    }

    if (sessionStorage.getItem('rangoPag') !== null) {
      this.rangoPag = parseInt(sessionStorage.getItem('rangoPag'), 10);
    }

  }

  // Consulta y carga los filtros para el usuario
  cargarFiltros(): any {
    this._SOperacionesReciprocasService.CargarFiltrosTransacciones().subscribe(
      response => {
        this.annios = response.result[0].annios;
        this.estados = response.result[0].estados;
        this.datosMultSlc = response.result[0].fechasConsolidacion;
        this.periodos = response.result[0].periodos;
        this.periodos.unshift({ codigo: null, descripcion: '' });
        sessionStorage.setItem('filtroEstado', JSON.stringify(this.estados)); // Se recupera desde el detalle
        this.auxdatosMultSlc = this.datosMultSlc;

      }, e => {
        console.error(e);
      }, () => { }
    );
  }

  mostrarhora(): string {
    const f = new Date();
    const cad = f.getHours() + ':' + f.getMinutes() + ':' + f.getSeconds();
    window.status = cad;
    return cad;
  }

  // Redirige al usuario a la página de detalle, consultando los valores con que la vista debe ser cargada
  IrADetalle(idOpRecip: string) {
    sessionStorage.setItem('idOpRecip', idOpRecip);
    this.router.navigate(['/Transacciones/Detalle']);
  }

  // Método para filtrar
  Filtrar(slctPeriodo, slctAnnio, slctFecha, slctEstado, entidadesInv, idEntidad, idTransaccion) {

    // Control de indefinidos
    if (slctPeriodo === undefined) {
      slctPeriodo = '';
    }

    if (slctFecha === undefined) {
      slctFecha = '';
    }

    if (slctEstado === undefined) {
      slctEstado = '';
    }

    // Se construye el body como objeto JAVASCRIPT OBJECT NOTATION
    const body = JSON.stringify({
      annio: slctAnnio,
      codEstado: slctEstado.codigo,
      entidadesInv: entidadesInv,
      fechaConsolidacion: slctFecha,
      idEntidad: idEntidad,
      idTransaccion: idTransaccion,
      periodo: slctPeriodo.codigo
    });

    // Se almacena en una variable de clase para aumentar su scope
    this.bodyFiltros = body;
    sessionStorage.setItem('Filtros', body); // Y también se almacena en sessionStorage para ser recuperada posteriormente
    this.spinner.show();
    // A continuación se consulta con valor '0' para que traiga la primera página
    this._SOperacionesReciprocasService.ConsultarTransacciones(body, this.numTransaccionesxVista.toString(), '0').subscribe(
      response => {
        this.spinner.hide();
        this.transacciones = response.result[0].transacciones.content; // Obtiene las transacciones
        if (this.transacciones.length === 0) {
          swal({
            type: 'info',
            title: 'No hay datos',
            text: 'No hay registros relacionados con tu búsqueda.'
          });
          this.transacciones = undefined;
          return;
        }
        console.table(this.transacciones);
        this.ultimaPag = response.result[0].transacciones.totalPages; // Obtiene el total de páginas
        this.numPaginaActual = response.result[0].transacciones.number; // Obtiene la página actual
        this.rangoPag = response.result[0].transacciones.number;
        // Guarda las transacciones como objeto json en session
        sessionStorage.setItem('TablaTransacciones', JSON.stringify(this.transacciones));
        sessionStorage.setItem('numPaginaActual', this.numPaginaActual.toString()); // Guarda la página actual
        sessionStorage.setItem('numUltimaPagina', this.ultimaPag.toString()); // Guarda la última página
        sessionStorage.setItem('rangoPag', this.rangoPag.toString()); // Guarda rango página
      }, e => {
        this.spinner.hide();
        if (e.status === 0) {
          e.error.message = 'Ha ocurrido un error, por favor intenta nuevamente más tarde.';
        }
        swal({
          type: 'error',
          title: 'Error',
          text: e.error.message
        });
      }, () => {
      }
    );
  }

  // Método para paginar. El parámetro se obtiene desde la vista
  Paginar(param: string): void {
    this.spinner.show();
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
    this._SOperacionesReciprocasService.ConsultarTransacciones(this.bodyFiltros, this.numTransaccionesxVista.toString(), paginaDestino)
      .subscribe(
        response => {
          this.spinner.hide();
          this.transacciones = response.result[0].transacciones.content; // Obtiene transacciones
          this.numPaginaActual = response.result[0].transacciones.number; // Obtiene número de pagina actual
          sessionStorage.setItem('TablaTransacciones', JSON.stringify(this.transacciones)); // Actualiza tabla de transacciones en session
          sessionStorage.setItem('numPaginaActual', this.numPaginaActual.toString()); // Guarda número de la página actual
          sessionStorage.setItem('rangoPag', this.rangoPag.toString()); // Guarda número de la página actual
        }, e => {
          this.spinner.hide();
          console.error('Error consultando las transacciones: ', e);
        }, () => {
        }
      );
  }

  // Establece el valore para el select de fecha de consolidación
  handlSelect(slctMultiple) {
    this.hacerInvisible();
    this.FormFiltrosPrim.controls['fechaConsolidacion'].setValue(slctMultiple);
  }
  // Filtra las fechas dependiendo del valor que está siendo introducido por el usuario
  filtroFecha($event) {
    // Revisa si lo contiene
    if ($event !== undefined && $event !== '' && this.datosMultSlc !== undefined) {
      this.auxdatosMultSlc = this.datosMultSlc.filter(dato => dato.indexOf($event) !== -1);
    }
  }

  // Hace visible el campo de selección multiple de fecha de consolidación
  hacerVisible() {
    this.optVisible = true;
  }

  // Hace invisible el campo de selección multiple de fecha de consolidación
  hacerInvisible() {
    setTimeout(() => {
      this.optVisible = false;
    }, 500);
  }

  downloadFile(base64, nombre, tipo) {
    const filePdf = atob(base64);
    const arraybuffer = new ArrayBuffer(filePdf.length);
    const view = new Uint8Array(arraybuffer);
    for (let i = 0; i < filePdf.length; i++) {
      // tslint:disable-next-line:no-bitwise
      view[i] = filePdf.charCodeAt(i) & 0xff;
    }
    const blob = new Blob([arraybuffer], { type: tipo });
    FileSaver.saveAs(blob, nombre);
  }

  formatDate(date) {
    return date.split('-')[2] + '/' + date.split('-')[1] + '/' + date.split('-')[0];
  }

  getExcelFromTable() {
    this.spinner.show();
    this._SDescargaTransaccionesService.descargarTransacciones(this.bodyFiltros).subscribe(
      response => {
        this.spinner.hide();
        if (response.result[0].fileTransacciones != null) {
          const actual = new Date().toISOString().split('T')[0];
          this.downloadFile(response.result[0].fileTransacciones, 'Transacciones-' + this.formatDate(actual) + '.xlsx', 'application/xlsx');
        }
      }, error => {
        this.spinner.hide();
        if (error.status === 0) {
          error.error.message = 'Ha ocurrido un error, por favor intenta nuevamente más tarde.';
        }
        swal({
          type: 'error',
          title: 'Error',
          text: error.error.message
        });
      }
    );
  }

  ngAfterViewInit() { // control para responsive inicial al cargar la vista
    const widthInicial = document.body.clientWidth;
    if (widthInicial < 1200) {
      this.handlerClasesPeq();
      if (widthInicial < 768) {
        // this.renderer.removeClass(this.divPrimFilt.nativeElement, 'row');
        if (widthInicial < 576) {
          this.renderer.addClass(this.inpFiltFecha.nativeElement, 'fechaFiltResponsive');
        }
      }
    }
  }

  // Control de width de la pantalla para ajustar clases cuando el usuario redimensiona
  @HostListener('window:resize', ['$event'])
  onResize(event) {

    // Let widthWindow = window.innerWidth;
    const widthWindow = document.body.clientWidth;

    if (widthWindow < 1200) {
      this.handlerClasesPeq();
      if (widthWindow < 768) {
        // if (widthWindow < 576) {
        // }
        this.renderer.addClass(this.inpFiltFecha.nativeElement, 'fechaFiltResponsive');
        // this.renderer.removeClass(this.divPrimFilt.nativeElement, 'row');
      } else {
        // this.renderer.addClass(this.divPrimFilt.nativeElement, 'row');
        this.renderer.removeClass(this.inpFiltFecha.nativeElement, 'fechaFiltResponsive');
      }
    } else if (widthWindow > 1200) {
      this.handlerClasesGra();
    }
  }

  // Manejadores de clases para resoluciones grandes y pequeñas

  handlerClasesPeq() {
    this.renderer.removeClass(this.tableTabla.nativeElement, 'table');
    this.renderer.removeClass(this.tableTabla.nativeElement, 'table-sm');
    this.renderer.addClass(this.tableTabla.nativeElement, 'responsive-table');
  }

  handlerClasesGra() {
    this.renderer.addClass(this.tableTabla.nativeElement, 'table');
    this.renderer.addClass(this.tableTabla.nativeElement, 'table-sm');
    this.renderer.removeClass(this.tableTabla.nativeElement, 'responsive-table');
  }


  changeValEstado(estado) {
    this.valEstadoObject = estado;
    this.valEstado = estado.descripcion;
    this.mostrarEstados = 'none';
  }

  showEstados() {
    this.mostrarEstados = this.mostrarEstados === 'block' ? 'none' : 'block';
  }

  changeValPeriodo(periodo) {
    this.valPeriodoObject = periodo;
    this.valPeriodo = periodo.descripcion;
    this.mostrarPeriodos = 'none';
  }

  showPeriodos() {
    this.mostrarPeriodos = this.mostrarPeriodos === 'block' ? 'none' : 'block';
  }

  hidePeriodos() {
    this.mostrarPeriodos = 'none';
  }

  changeValAnnio(annio) {
    this.valAnnio = annio;
    this.mostrarAnnios = 'none';
  }

  showAnnios() {
    this.mostrarAnnios = this.mostrarAnnios === 'block' ? 'none' : 'block';
  }

  hideAnnios() {
    this.mostrarAnnios = 'none';
  }

  hideEstados() {
    this.mostrarEstados = 'none';
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) {
    const top = document.getElementById('start');
    const top2 = document.getElementById('start2');
    const top3 = document.getElementById('start3');
    let list = null;
    let current = null;
    if (top === document.activeElement) {
      list = document.getElementById('list');
      current = top;
    } else if (top2 === document.activeElement) {
      list = document.getElementById('list2');
      current = top2;
    } else if (top3 === document.activeElement) {
      list = document.getElementById('list3');
      current = top3;
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
    this.hidePeriodos();
    this.hideAnnios();
    this.hideEstados();
  }

  hideAllEmitter($event) {
    if ($event) { this.hideAll(); }
  }
}
