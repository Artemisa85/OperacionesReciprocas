import { Component, OnInit } from '@angular/core';
import { SCargaArchivosService } from 'src/app/services/s-carga-archivos.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { CDetalleORComponent } from '../c-detalle-or.component';

@Component({
  selector: 'app-c-adjuntos',
  templateUrl: './c-adjuntos.component.html',
  styleUrls: ['./c-adjuntos.component.css', '../../../../assets/libs/bootstrap/css/bootstrap.min.css']
})
export class CAdjuntosComponent implements OnInit {

  public NombreCont: string;
  public EmailCont: string;
  public ContactoCont: string;
  public ContactoCont2: string;

  constructor(
    private cDetalle: CDetalleORComponent) {
  }

  ngOnInit() {
    this.dividirCadena(this.cDetalle.contactoContador);//, '\n\r');
  }



  dividirCadena(cadenaADividir) {
    let separador;
    separador = cadenaADividir.lastIndexOf('\n') == -1 ? '\n\r' : '\n';
    var arrayDeCadenas = cadenaADividir.split(separador);
    this.NombreCont = arrayDeCadenas[0];
    this.EmailCont = arrayDeCadenas[1];
    this.ContactoCont = arrayDeCadenas[2];

  }

}
