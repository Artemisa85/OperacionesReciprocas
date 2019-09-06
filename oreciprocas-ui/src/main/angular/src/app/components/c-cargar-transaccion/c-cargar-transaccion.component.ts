import { Component, OnInit, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { SCargarTransaccionesService } from '../../services/s-carga-transacciones.service';
import { NgxSpinnerService } from 'ngx-spinner';
import * as FileSaver from 'file-saver';
import swal from 'sweetalert2';

@Component({
  selector: 'app-c-cargar-transaccion',
  templateUrl: './c-cargar-transaccion.component.html',
  styleUrls: ['./c-cargar-transaccion.component.css', '../../../assets/libs/bootstrap/css/bootstrap.min.css']
})
export class CCargarTransaccionComponent implements OnInit {
  public archivoACargar: any;
  public TiposPermitidos = ['xls', 'xlsx'];
  public msgTiposPermitidos = 'El archivo no corresponde con un tipo de archivo permitido, adjunta un archivo de tipo Excel';
  public tamMaxArch = 5242880; // 5 MB en Bytes
  public msgErrorPeso = `El tamaño del archivo supera el tamaño permitido: ${this.tamMaxArch / 1048576}MB.`;
  public errorFile: any;
  public showErrorResume = false;
  public transaccionesCargadas = 0;
  public showOkResume = false;

  @ViewChild('inputFileBrowser') inputFileBrowser: ElementRef;

  constructor(private _renderer2: Renderer2, private _SCargarTransacciones: SCargarTransaccionesService, private spinner: NgxSpinnerService) {
    this.archivoACargar = null;
    this.resetLoad();
  }

  ngOnInit() {
    this.archivoACargar = null;
    this.resetLoad();
  }

  onFilesChange(fileList: Array<File>) {
    this.verificarySubir(fileList);
  }

  ClickFileInput() {
    this._renderer2.selectRootElement(this.inputFileBrowser.nativeElement).click();
  }

  EliminarArchivo() {
    this.archivoACargar = null;
  }

  adjuntarArchivo(files: FileList) {
    for (let index = 0; index < files.length; index++) {
      const ext = files[index].name.split('.')[files[index].name.split('.').length - 1];
      if (files[index].size > this.tamMaxArch) {
        swal({
          type: 'error',
          title: 'Error',
          text: this.msgErrorPeso
        });
        return;
      } else if (this.TiposPermitidos.lastIndexOf(ext.toLowerCase()) === -1) {
        swal({
          type: 'error',
          title: 'Error',
          text: this.msgTiposPermitidos
        });
        return;
      }
    }
    this.verificarySubir(files);
  }

  private verificarySubir(files: any): any {
    if (files.length > 1) {
      swal({
        type: 'error',
        title: 'Error',
        text: 'Se puede cargar solo de a un elemento a la vez'
      });
      return;
    } else {
      this.showErrorResume = false;
      this.showOkResume = false;
      this.errorFile = null;
      this.archivoACargar = files[0];
      this.archivoACargar.urlIcono = './assets/icons/excel.png';
    }
    console.log(this.archivoACargar);
  }

  getBase64(file) {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result.split(',')[1]);
      reader.onerror = error => reject(error);
    });
  }

  cargarArchivo() {
    this.resetLoad();
    this.spinner.show();
    this.getBase64(this.archivoACargar).then(
      (data) => {
        console.log(data);
        this._SCargarTransacciones.cargarTransacciones(data).subscribe(
          response => {
            this.spinner.hide();
            console.log(JSON.stringify(response));
            if (response.result[0].errorFile != null) {
              this.errorFile = response.result[0].errorFile;
              this.showErrorResume = true;
            } else if (response.result[0].transaccionesCargadas > 0) {
              this.transaccionesCargadas = response.result[0].transaccionesCargadas;
              this.showOkResume = true;
            } else {
              swal({
                type: 'error',
                title: 'Error',
                text: 'Error en la estructura del archivo de cargue, revisa detalle de estructura'
              });
            }
          }, error => {
            this.spinner.hide();
            swal({
              type: 'error',
              title: 'Error',
              text: 'Error en la estructura del archivo de cargue, revisa detalle de estructura'
            });
          }
        );
      }
    ).catch(
      (error) => {
        console.log('Error transformando a bytearray');
      }
    );
  }

  downloadErrorFile() {
    this.downloadFile(this.errorFile, 'RegistroDeErroresCargue', 'text/plain');
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

  resetLoad() {
    this.errorFile = null;
    this.showErrorResume = false;
    this.transaccionesCargadas = 0;
    this.showOkResume = false;
  }

}
