import { Component, OnInit, HostListener, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { CEnvioMensajeComponent } from './c-envio-mensaje/c-envio-mensaje.component';
import { NgxSpinnerService } from 'ngx-spinner';
import { SOperacionesReciprocasService } from 'src/app/services/s-operaciones-reciprocas.service';
import { CTransaccionesComponent } from '../c-transacciones/c-transacciones.component';
import { CComentariosComponent } from './c-comentarios/c-comentarios.component';
import swal from 'sweetalert2';
import { SInicioSesionService } from 'src/app/services/s-inicio-sesion.service';

@Component({
  selector: 'app-c-detalle-or',
  templateUrl: './c-detalle-or.component.html',
  styleUrls: ['./c-detalle-or.component.css']
})
export class CDetalleORComponent implements OnInit {

  public formEstado: FormGroup;

  public strNuevoEstado; // macena el valor del nuevo estado
  public strNuevoEstadoObject;
  public estadosPosibles: any; // Array contenedor de la colección de estados
  public idOpRecip; // Identificación de la operación recíproca

  public arrayComentarios;
  public errorCambioEstado: boolean;

  public autoridadUser: string;
  public mostrarEstados;

  showGestion: boolean;

  // Datos de detalle de la operación recíproca
  // TODO Construir el modelo cuando esté finalizada
  idTransaccion: string;
  entidadesInvolucradas: any;
  contactoContador: any;
  estadoAct: any;
  fechaConsolidacion: string;
  fechaCargue: string;
  operacionesFNA = [];
  operacionesCGN = [];
  operacionesGeneral = [];
  comodin1: string;
  comodin2: string;
  comodin3: string;
  comodin4: string;
  comodin5: string;
  esObsoleto: boolean;

  // Para ajustes reponsive sobre el DOM
  @ViewChild('divAjuste1') divAjuste1: ElementRef;
  @ViewChild('divAjuste2') divAjuste2: ElementRef;
  @ViewChild('divAjuste3') divAjuste3: ElementRef;
  @ViewChild('divAjuste4') divAjuste4: ElementRef;
  @ViewChild('divAjuste5') divAjuste5: ElementRef;
  @ViewChild('divAjuste6') divAjuste6: ElementRef;
  @ViewChild('divAjuste7') divAjuste7: ElementRef;
  @ViewChild('divAjuste8') divAjuste8: ElementRef;
  @ViewChild('divAjuste9') divAjuste9: ElementRef;
  @ViewChild('divAjuste10') divAjuste10: ElementRef;
  @ViewChild('divAjuste11') divAjuste11: ElementRef;
  @ViewChild('divAjuste12') divAjuste12: ElementRef;
  @ViewChild('divCodCuenta') divCodCuenta: ElementRef;
  @ViewChild('divCodCuenta2') divCodCuenta2: ElementRef;
  @ViewChild('spanEstado') spanEstado: ElementRef;

  // Obtiene data desde el componente cEnvioMensaje, hijo de este componente
  @ViewChild(CEnvioMensajeComponent) CEnvMensaje: CEnvioMensajeComponent;

  // Para autoScroll
  @ViewChild('divPivot') divPivot: ElementRef; // Utilizado como apuntador para scroll
  @ViewChild('sltEstado') sltEstado: ElementRef;

  // Almacena los detalles de la transacción
  responseDetalle: any;

  constructor(
    private router: Router
    , private renderer: Renderer2
    , private fb: FormBuilder
    , private _NgxSpinnerService: NgxSpinnerService
    , private _SOperacionesReciprocasService: SOperacionesReciprocasService
    , private CComentarios: CComentariosComponent
    , private _SInicioSesionService: SInicioSesionService
  ) {
    this.mostrarEstados = 'none';
    this.showGestion = false;
  }

  buildFormEstado() {
    this.formEstado = this.fb.group({
      sltEstado: [null, Validators.required]
    });
  }

  ngOnInit() {
    this.idOpRecip = sessionStorage.getItem('idOpRecip');
    this._SInicioSesionService.ROL.subscribe(value => this.autoridadUser = value.toString());
    this.CargarOpReciproca(this.idOpRecip);
    window.scrollTo(0, 0);
    this.estadosPosibles = JSON.parse(sessionStorage.getItem('filtroEstado'));
    this.buildFormEstado();
    this.strNuevoEstado = '';
    this.consultarComentarios();
  }

  public consultarComentarios() {
    const idOpRecip = sessionStorage.getItem('idOpRecip');
    this._NgxSpinnerService.show();
    this._SOperacionesReciprocasService.consultarComentarios(parseInt(idOpRecip)).subscribe(
      res => {
        this.arrayComentarios = res.result[0].comentarios.reverse();
        this._NgxSpinnerService.hide();
      }, err => this._NgxSpinnerService.hide()
    );

  }

  CargarOpReciproca(idOpRecip: string): void {
    this._NgxSpinnerService.show();
    this._SOperacionesReciprocasService.ConsultarDetallesOp(idOpRecip).subscribe(
      (response) => {
        // Se almacena en un objeto de clase local
        this.responseDetalle = response.result[0];
        // Carga data
        this.idTransaccion = this.responseDetalle.idTransaccion;
        this.entidadesInvolucradas = this.responseDetalle.entidadesInvolucradas;
        this.contactoContador = this.responseDetalle.contactoContador;
        this.estadoAct = this.responseDetalle.estado;
        this.fechaConsolidacion = this.responseDetalle.fechaConsolidacion;
        this.fechaCargue = this.responseDetalle.fechaCargue;
        this.operacionesCGN = this.responseDetalle.operacionesCGN;
        this.operacionesFNA = this.responseDetalle.operacionesFNA;
        this.operacionesGeneral = this.responseDetalle.operacionesOtros;
        this.strNuevoEstado = this.responseDetalle.estado.descripcion;
        this.esObsoleto = this.estadoAct.codigo === 'OBSOLETO';
        this.comodin1 = this.responseDetalle.comodin1;
        this.comodin2 = this.responseDetalle.comodin2;
        this.comodin3 = this.responseDetalle.comodin3;
        this.comodin4 = this.responseDetalle.comodin4;
        this.comodin5 = this.responseDetalle.comodin5;
        sessionStorage.setItem('estadoActual', this.estadoAct.codigo);
        sessionStorage.setItem('cambioManualEstado', 'false');
        this._NgxSpinnerService.hide();
      }, err => {
        this._NgxSpinnerService.hide();
      }, () => {
      }
    );

  }

  ngAfterViewInit() {

    let widthWindow = window.innerWidth;
    if (widthWindow < 992) { // Se controla tanto la redimensión como la creación
      // this.adaptacionWidthPeq();
    }
  }

  IrAtras() {
    this.router.navigate(['/Transacciones']);
  }

  // Añade clases de forma segura, al DOM
  private anadirClase(elemento: ElementRef, clase: string): void {
    this.renderer.addClass(elemento.nativeElement, clase);
  }
  // Remueve clases de forma segura, al DOM
  private removerClase(elemento: ElementRef, clase: string): void {
    this.renderer.removeClass(elemento.nativeElement, clase);
  }

  public cambiarEstado(slctEstado) {
    this.errorCambioEstado = false;
    this.mostrarEstados = 'none';

    if (this.autoridadUser === 'ROLE_EXTERNAL_USER') {
      swal({
        type: 'error',
        title: 'Autorización inválida',
        text: 'Tu no puedes realizar esta acción'
      });
      setTimeout(() => {
        this.strNuevoEstado = '';
      }, 0);
      return;
    }

    const idOperacion = this.idOpRecip;

    if (slctEstado.codigo === null) {
      swal({
        type: 'warning',
        title: 'Advertencia',
        text: 'Debes seleccionar un estado nuevo'
      });
      setTimeout(() => {
        this.strNuevoEstado = '';
      }, 0);
      return;
    } else if (this.CEnvMensaje.txtComentarios.length < 1) {
      this.divPivot.nativeElement.scrollIntoView();
      this.CEnvMensaje.txtAreaComent.nativeElement.focus(); // Se hace un focus al campo de texto
      this.errorCambioEstado = true;
      setTimeout(() => {
        sessionStorage.setItem('estadoActual', slctEstado.codigo);
        sessionStorage.setItem('cambioManualEstado', 'true');
        this.strNuevoEstado = slctEstado.descripcion;
        this.CEnvMensaje.txtAreaComent.nativeElement.blur();
        this.sltEstado.nativeElement.blur();
        // Se cambia el valor al final del blur para que el doble binding tenga efecto y cambie el valor del select. 
        this.strNuevoEstado = '';
      }, 420);
      return;
    }
    // this._NgxSpinnerService.show();
    /*
    this._SOperacionesReciprocasService.cambiarEstado(this.strNuevoEstado, idOperacion, this.CEnvMensaje.txtComentarios).subscribe(
      response => {
        // Hacer nuevo llamado para recargar todos los datos
        // this.CargarOpReciproca(this.idOpRecip);
        // this._NgxSpinnerService.hide();
      }, error => {
        // this._NgxSpinnerService.hide();
      }, () => {
      }
    );*/

  }

  comentarioEnviado($event) {
    this.consultarComentarios();
    this.CEnvMensaje.txtComentarios = '';
    this.CEnvMensaje.archivosASubir = [];
    this.estadoAct.descripcion = $event.descEstadoTransaccion;
    this.strNuevoEstado = this.estadoAct.descripcion;
    this.estadoAct.codigo = $event.codEstadoTransaccion;
    this.esObsoleto = this.estadoAct.codigo === 'OBSOLETO';
    sessionStorage.removeItem('TablaTransacciones');
    sessionStorage.removeItem('estadoActual');
    sessionStorage.removeItem('cambioManualEstado');
  }

  // Control de width de la pantalla para ajustar clases cuando el usuario redimensiona
  @HostListener('window:resize', ['$event'])
  onResize(event) {

    let widthWindow = window.innerWidth;

    if (widthWindow < 992) {
      this.adaptacionWidthPeq();
    } else if (widthWindow > 991) {
      this.adaptacionWidthGra();
    }
  }

  // Adaptación de dimensiones
  private adaptacionWidthPeq(): void {
    // Se remueven clases de división de columnas
    this.removerClase(this.divAjuste1, 'col-sm-2');
    this.removerClase(this.divAjuste2, 'col-sm-2');
    this.removerClase(this.divAjuste3, 'col-sm-4');
    this.removerClase(this.divAjuste4, 'col-sm-4');
    this.removerClase(this.divAjuste5, 'col-sm-2');
    this.removerClase(this.divAjuste6, 'col-sm-2');
    this.removerClase(this.divAjuste7, 'col-sm-4');
    this.removerClase(this.divAjuste8, 'col-sm-4');
    this.removerClase(this.divAjuste9, 'col-sm-2');
    this.removerClase(this.divAjuste10, 'col-sm-2');
    this.removerClase(this.divAjuste11, 'col-sm-4');
    this.removerClase(this.divAjuste12, 'col-sm-4');
    //this.removerClase(this.spanEstado, 'col-sm-4');
    // Se añaden nuevos valores para las columnas
    this.anadirClase(this.divAjuste1, 'col-sm-6');
    this.anadirClase(this.divAjuste2, 'col-sm-6');
    this.anadirClase(this.divAjuste3, 'col-sm-6');
    this.anadirClase(this.divAjuste4, 'col-sm-6');
    this.anadirClase(this.divAjuste5, 'col-sm-6');
    this.anadirClase(this.divAjuste6, 'col-sm-6');
    this.anadirClase(this.divAjuste7, 'col-sm-6');
    this.anadirClase(this.divAjuste8, 'col-sm-6');
    this.anadirClase(this.divAjuste9, 'col-sm-6');
    this.anadirClase(this.divAjuste10, 'col-sm-6');
    this.anadirClase(this.divAjuste11, 'col-sm-6');
    this.anadirClase(this.divAjuste12, 'col-sm-6');
    //this.anadirClase(this.spanEstado, 'col-sm');

    this.anadirClase(this.divCodCuenta, 'ajusteDivDatos');
    this.anadirClase(this.divCodCuenta2, 'ajusteDivDatos');

    // this.renderer.setStyle(this.divCodCuenta,'max-width','80%');
  }

  private adaptacionWidthGra(): void {
    // Se remueven clases de división de columnas
    this.removerClase(this.divAjuste1, 'col-sm-6');
    this.removerClase(this.divAjuste2, 'col-sm-6');
    this.removerClase(this.divAjuste3, 'col-sm-6');
    this.removerClase(this.divAjuste4, 'col-sm-6');
    this.removerClase(this.divAjuste5, 'col-sm-6');
    this.removerClase(this.divAjuste6, 'col-sm-6');
    this.removerClase(this.divAjuste7, 'col-sm-6');
    this.removerClase(this.divAjuste8, 'col-sm-6');
    this.removerClase(this.divAjuste9, 'col-sm-6');
    this.removerClase(this.divAjuste10, 'col-sm-6');
    this.removerClase(this.divAjuste11, 'col-sm-6');
    this.removerClase(this.divAjuste12, 'col-sm-6');
    //this.removerClase(this.spanEstado, 'col-sm');
    // Se añaden nuevos valores para las columnas
    this.anadirClase(this.divAjuste1, 'col-sm-2');
    this.anadirClase(this.divAjuste2, 'col-sm-2');
    this.anadirClase(this.divAjuste3, 'col-sm-4');
    this.anadirClase(this.divAjuste4, 'col-sm-4');
    this.anadirClase(this.divAjuste5, 'col-sm-2');
    this.anadirClase(this.divAjuste6, 'col-sm-2');
    this.anadirClase(this.divAjuste7, 'col-sm-4');
    this.anadirClase(this.divAjuste8, 'col-sm-4');
    this.anadirClase(this.divAjuste9, 'col-sm-2');
    this.anadirClase(this.divAjuste10, 'col-sm-2');
    this.anadirClase(this.divAjuste11, 'col-sm-4');
    this.anadirClase(this.divAjuste12, 'col-sm-4');
    //this.anadirClase(this.spanEstado, 'col-sm-4');

    this.removerClase(this.divCodCuenta, 'ajusteDivDatos');
    this.removerClase(this.divCodCuenta2, 'ajusteDivDatos');
    // this.renderer.removeStyle(this.divCodCuenta,'max-width');
  }
  /*changeValEstado(estado) {
    this.strNuevoEstado = estado.descripcion;
    this.mostrarEstados = 'none';
  }*/

  showEstados() {
    if (this.autoridadUser === 'ROLE_FNA_ADMIN' && !this.esObsoleto) {
      this.mostrarEstados = this.mostrarEstados === 'block' ? 'none' : 'block';
    }
  }

  hideEstados() {
    this.mostrarEstados = 'none';
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) {
    const top6 = document.getElementById('start6');
    let list = null;
    let current = null;
    if (top6 === document.activeElement) {
      list = document.getElementById('list6');
      current = top6;
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

}
