import { Injectable, ɵConsole } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SOperacionesReciprocasService } from './s-operaciones-reciprocas.service';
import { CComentariosComponent } from '../components/c-detalle-or/c-comentarios/c-comentarios.component';

@Injectable({
  providedIn: 'root'
})
export class SEnvioMensajeService {
  [x: string]: any;

    private url_servidor: string = environment.URL_BASE;

    constructor(
        private http: HttpClient
      ) { }

    public EnviarComentario(comentario: string): Observable<any> {
      const idOpRecip = sessionStorage.getItem('idOpRecip').toString();
      const codEstado = sessionStorage.getItem('estadoActual');
      const cambioManual = sessionStorage.getItem('cambioManualEstado');
      const tipoUsuario = sessionStorage.getItem('TipoUsuario');
      let nombreAutor = '';
      if (tipoUsuario === 'ext') {
        nombreAutor = sessionStorage.getItem('Entidades');
      } else {
        nombreAutor = sessionStorage.getItem('nombreUsuario');
      }

      // console.log('Código estado antes de enviar comentario: ' + codEstado + ' Es cambio manual: ' + cambioManual);
      const body = JSON.stringify(
        {
          idTransaccion: idOpRecip,
          texto: comentario,
          codEstado: codEstado,
          cambioManual: cambioManual,
          nombreAutor: nombreAutor
        });
      return this.http.post(this.url_servidor + '/api/comentarios', JSON.parse(body));
    }

    public EnviarArchivos(archivos: any, idComentario: string): Observable<any> {
      console.log(idComentario);
      const formData = new FormData();
      let contador = 0;
      // Acceder a cada uno de los archivos que vienen en la lista
      for (let i = 0; i < archivos.length; i++, contador++) {
        // Se almacenan en un formData
        console.log('Envía: ', archivos[i], ', con nombre: ', archivos[i].name);
        formData.append('archivos', archivos[i], archivos[i].name);
      }
      // console.log(formData.get('llave'));
      // Se envía el FormData en el body del request post
      return this.http.post(this.url_servidor + '/api/comentarios/' + idComentario + '/cargar-archivos', formData);
    }

    public obtenerTiposPermitidos(): Observable<any> {
      return this.http.get(this.url_servidor + '/api/opciones/tipo-archivos-permitidos');
    }

}
