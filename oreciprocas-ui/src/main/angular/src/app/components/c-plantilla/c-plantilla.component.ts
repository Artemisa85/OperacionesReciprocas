import { Component, OnInit, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { SPlantillasService } from '../../services/s-gestion-plantillas.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-c-plantilla',
  templateUrl: './c-plantilla.component.html',
  styleUrls: ['./c-plantilla.component.css',  '../../../assets/libs/bootstrap/css/bootstrap.min.css']
})
export class CPlantillaComponent implements OnInit {

  private plantillas: any;
  isInDetalle: boolean;
  private plantillaDetalle: any;
  @ViewChild('activoSi') activoSi: ElementRef;
  @ViewChild('activoNo') activoNo: ElementRef;
  @ViewChild('preview') preview: ElementRef;

  constructor(private gestionPlantillas: SPlantillasService, private renderer: Renderer2) {
    this.isInDetalle = false;
  }

  ngOnInit() {
    this.getPlantillas();
  }

  getPlantillas() {
    this.gestionPlantillas.getPlantillas().subscribe(
      response => {
        this.plantillas = response.result[0].plantillas;
      }, error => {
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

  IrADetallePlantilla(plantilla) {
    this.plantillaDetalle = plantilla;
    this.isInDetalle = true;
    setTimeout(() => {
      this.plantillaDetalle.activo ? this.activarPlantilla() : this.desactivarPlantilla();
      this.renderer.setProperty(this.preview.nativeElement, 'innerHTML', this.plantillaDetalle.contenido);
    }, 0);

  }

  volverDetalle() {
    this.isInDetalle = false;
    this.getPlantillas();
  }

  activarPlantilla() {
    this.plantillaDetalle.activo = true;
    this.renderer.removeClass(this.activoNo.nativeElement, 'active');
    this.renderer.addClass(this.activoSi.nativeElement, 'active');
  }

  desactivarPlantilla() {
    this.plantillaDetalle.activo = false;
    this.renderer.removeClass(this.activoSi.nativeElement, 'active');
    this.renderer.addClass(this.activoNo.nativeElement, 'active');
  }

  changePreview() {
    this.renderer.setProperty(this.preview.nativeElement, 'innerHTML', this.plantillaDetalle.contenido);
  }

  guardarPlantilla() {
    this.gestionPlantillas.actualizarPlantilla(this.plantillaDetalle).subscribe(
      response => {
        this.isInDetalle = false;
        this.getPlantillas();
      }, error => {
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
}
