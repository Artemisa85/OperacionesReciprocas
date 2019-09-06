import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

@Injectable({
    providedIn: 'root'
  })
export class SCargaArchivosService {

    public url_servidor = "http://servicioqueno.existe";


    constructor(private http: HttpClient) { }

    public postFile(archivo: File) {
        const formData = new FormData();
        // append(nombreenelservidor, objimagen, nombreimagen)
        formData.append('imagenPropia', archivo, archivo.name);
        return this.http.post(this.url_servidor, formData);
      
    }

}