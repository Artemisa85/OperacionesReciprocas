import { Component, OnInit, Input } from '@angular/core';
import { SOperacionesReciprocasService } from 'src/app/services/s-operaciones-reciprocas.service';

@Component({
  selector: 'app-c-comentarios',
  templateUrl: './c-comentarios.component.html',
  styleUrls: ['./c-comentarios.component.css',  '../../../../assets/libs/bootstrap/css/bootstrap.min.css'],

})
export class CComentariosComponent implements OnInit {
  
  rolUsuario: string;

  @Input() public arrComentarios: Array<any> = [];


  constructor(
    private _SOperacionesReciprocasService: SOperacionesReciprocasService
  ) { }

  ngOnInit() {

    this.rolUsuario = sessionStorage.getItem("TipoUsuario") === 'ext' ? 'EXTERNAL_USER' : 'FNA_ADMIN'

  }

  public descargarAdjunto(adjunto): void {
    this._SOperacionesReciprocasService.descargarAdjunto(adjunto).subscribe(
      res => {
        if (window.navigator && window.navigator.msSaveOrOpenBlob) {
          window.navigator.msSaveOrOpenBlob(res, adjunto.nombre);
        } else {
          const url = window.URL.createObjectURL(res);
          let a = document.createElement('a');
          document.body.appendChild(a);
          a.setAttribute('style', 'display: none');
          a.href = url;
          a.download = adjunto.nombre;
          a.click();
          window.URL.revokeObjectURL(url);
        }
      }, error => {
        console.log(error);
      }, () => { });
  }
}
