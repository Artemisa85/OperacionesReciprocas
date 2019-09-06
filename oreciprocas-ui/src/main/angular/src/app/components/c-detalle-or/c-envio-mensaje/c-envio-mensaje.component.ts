import { Component, ViewChild, ElementRef, Renderer2, OnInit, NgModule, Output, EventEmitter, Input } from '@angular/core';
import { Router } from '@angular/router';
import { SEnvioMensajeService } from 'src/app/services/s-envio-mensaje.service';
import { getAppInitializer } from '@angular/router/src/router_module';
import { CComentariosComponent } from '../c-comentarios/c-comentarios.component';
import swal from 'sweetalert2';

@Component({
  selector: 'app-c-envio-mensaje',
  templateUrl: './c-envio-mensaje.component.html',
  styleUrls: ['./c-envio-mensaje.component.css', '../../../../assets/libs/bootstrap/css/bootstrap.min.css']
})
export class CEnvioMensajeComponent implements OnInit {

  public archivosASubir = [];
  public tiposPermitidos = [];
  public tamMaxArch = 5242880; // 5 MB en Bytes
  public msgErrorPeso: string;
  public msgErrorTipo: string;

  public txtComentarios: string = '';

  @ViewChild('inputFileBrowser') inputFileBrowser: ElementRef;
  @ViewChild('txtAreaComent') public txtAreaComent: ElementRef;

  @Output() private arrayChangeEmiter: EventEmitter<any> = new EventEmitter();

  @Input() public errCambioEstado: boolean; // Muestra o no división para solicitar un texto para cambiar el estado

  constructor(private router: Router,
    private _renderer2: Renderer2,
    private _SEnvioMensajeService: SEnvioMensajeService,
    private cComentariosC: CComentariosComponent
  ) { }

  ngOnInit() {
    this.msgErrorPeso = `El tamaño del archivo supera el tamaño permitido: ${this.tamMaxArch / 1048576}MB.`;
    this.msgErrorTipo = 'El archivo no corresponde con un tipo de archivo permitido, '
      + 'adjunta un archivo de tipo Word, Excel, Power Point, PDF, un mensaje de correo o una imagen';

    this._SEnvioMensajeService.obtenerTiposPermitidos().subscribe((response: any) => {
      this.tiposPermitidos = response.result.map( tipo => tipo.extension);
      console.log('tipos de archivo permitidos: ' + JSON.stringify(this.tiposPermitidos));
    }, error => {
      console.log(error);
      this.swalert('error', 'Ha ocurrido un error', 'Ocurrió un error enviando tu mensaje');
    }, () => { });
  }


  ClickFileInput() {
    this._renderer2.selectRootElement(this.inputFileBrowser.nativeElement).click();
  }

  // Carga un archivo
  public cargaArchivo(files: FileList) {

    // Carga y verifica extensiones
    for (let index = 0; index < files.length; index++) {
      let ext = files[index].name.split('.')[files[index].name.split('.').length - 1]; //Obtiene la extensión
      // lasIndexOf devuelve -1 si no se halla el objeto dentro del array
      if (files[index].size > this.tamMaxArch) {
        swal({
          type: 'error',
          title: 'Error',
          text: this.msgErrorPeso
        });
        return;
      } else if (this.tiposPermitidos.lastIndexOf(ext.toLowerCase()) !== -1) { } else {
        swal({
          type: 'warning',
          title: 'Advertencia',
          text: this.msgErrorTipo
        });
        return;
      }
    }
    /*Análisis de fileList para encontrar elementos que ya hayan sido cargados*/
    this.verificarySubir(files);
  }

  // Verifica que no exista ya un archivo subido, dentro de los archivos a subir
  private verificarySubir(files: any): any {

    let auxArchivosASubir = [];
    if (this.archivosASubir !== undefined && this.archivosASubir.length > 0) {
      // Si archivosASubir no es indefinido y tiene datos
      // Compara el contenido de archivosASubir y de files para ver si alguno de los elementos de files, ya se encuentra cargado
      for (let i = 0; i < this.archivosASubir.length; i++) {
        for (let j = 0; j < files.length; j++) {
          if (this.archivosASubir[i].name === files[j].name && this.archivosASubir[i].size === files[j].size) {
            swal({
              type: 'warning',
              title: 'Advertencia',
              text: 'Hay elementos iguales.'
            });
            return;
          }
        }
      }
      // Si no hay elementos iguales y archivosASubir ya tiene datos
      Array.prototype.push.apply(auxArchivosASubir, files); // Se convierte el filelist a array
      // Debe añadirse cada uno de los elementos contenidos en el array auxiliar
      for (var i = 0; i < auxArchivosASubir.length; i++) {
        this.archivosASubir.push(this.asignarIcono(auxArchivosASubir)[i]);
      }
    } else {
      // Si es indefinido o si archivosASubir no tiene datos, no se compara sino que solo se asigna el fileList
      Array.prototype.push.apply(auxArchivosASubir, files); // Se convierte el filelist a array
      // Se trata cada elemento del array para agregar una propiedad en donde se almacena el string del icono
      this.archivosASubir = this.asignarIcono(auxArchivosASubir);

    }
  }

  // Le asigna íconos a los archivos
  private asignarIcono(arrArchivos: any): any {
    for (let i = 0; i < arrArchivos.length; i++) {
      let ext = arrArchivos[i].name.split('.')[arrArchivos[i].name.split('.').length - 1]; // Obtiene la extensión
      switch (ext.toLowerCase()) {
        case 'xls': case 'xlsx': case 'xlsm': case 'ods':
          // Hojas de calculo
          arrArchivos[i].urlIcono = './assets/icons/excel.png';
          break;
        case 'pdf':
          // Pdf's
          arrArchivos[i].urlIcono = './assets/icons/pdf.png';
          break;
        case 'doc': case 'docx': case 'odt':
          // Procesador de textos
          arrArchivos[i].urlIcono = './assets/icons/word.png';
          break;
        case 'msg': case 'eml':
          arrArchivos[i].urlIcono = './assets/icons/correo.png';
          break;
        case 'odp': case 'pptx': case 'pptm': case 'ppt': case 'ppsx': case 'ppsm': case 'pps':
          // Presentaciones
          arrArchivos[i].urlIcono = './assets/icons/powerPoint.png';
          break;
        case 'jpg': case 'jpeg': case 'png': case 'jpe': case 'jfif': case 'bmp':
          // Imagenes
          arrArchivos[i].urlIcono = './assets/icons/imagen.png';
          break;
        default:
          arrArchivos[i].urlIcono = './assets/icons/archivo.png';
          break;
      }
    }
    return arrArchivos;
  }

  // Drag n drop
  onFilesChange(fileList: Array<File>) {
    this.verificarySubir(fileList);
  }

  // Obtiene los archivos invalidos procedentes desde la directiva
  onFileInvalids(fileList: Array<File>) {
    // this.invalidFiles = fileList;
  }

  // Elimina el archivo
  private EliminarArchivo(index, nombreArchivo): void {
    this.archivosASubir.splice(index, 1);
  }

  // Envía los archivos al servidor
  public SubirServidor(): void {
    if (!this.txtComentarios) {
      this.swalert('warning', 'Advertencia', 'Debes ingresar un comentario en el campo GESTIÓN.');
      return;
    } else {
      this._SEnvioMensajeService.EnviarComentario(this.txtComentarios).subscribe((response: any) => {
        if (this.archivosASubir.length !== 0) {
          this._SEnvioMensajeService.EnviarArchivos(this.archivosASubir, response.result[0].id).subscribe(res => {
            this.swalert('success', 'Mensaje enviado', 'Tu mensaje y tus archivos, han sido enviados exitosamente');
            this.arrayChangeEmiter.emit(response.result[0]);
          }, err => {
            this.swalert('error', 'Ha ocurrido un error al enviar tus archivos', err.error.message);
            this.arrayChangeEmiter.emit(response.result[0]);
          },
            () => { })
        } else {
          this.swalert('success', 'Mensaje enviado', 'Tu mensaje ha sido enviado exitosamente');
          this.arrayChangeEmiter.emit(response.result[0]);
        }
      }, error => {
        console.log(error);
        this.swalert('error', 'Ha ocurrido un error', 'Ocurrió un error enviando tu mensaje');
      }, () => { });
    }
  }

  // Permite o no el envío de archivos
  public permitirEnvio(): boolean {

    if (this.archivosASubir.length !== 0) { // si hay archivos, debe ingresar un comentario obligatorio
      if (this.txtComentarios.length !== 0) { return true; } else { return false; }
    } else if (this.txtComentarios.length !== 0) { return true; }

  }

  private swalert(type, title, text) {
    swal({
      type: type,
      title: title,
      text: text
    });
  }

}
