import { Directive, HostListener, HostBinding, EventEmitter, Output, Input } from '@angular/core';
import swal from 'sweetalert2';

@Directive({
  selector: '[dragndrop]'
})
export class DragnDropDirective {
    @Input() private allowed_extensions: Array<string> = [];
    @Input() private maxTam: number;
    @Input() private msgError: string;
    @Input() private msgAllowedExtensions: string;
    @Output() private filesChangeEmiter: EventEmitter<File[]> = new EventEmitter();
    
    @HostBinding('style.background') private background = '#eee';
  
    constructor() { }

    //Pasa mouse sobre el elemento
    @HostListener('mouseover', ['$event']) public onMouseOver(evt) {
      evt.preventDefault();
      evt.stopPropagation();
      this.background = '#999';
    }

    //Pasó mouse sobre el elemento
    @HostListener('mouseleave', ['$event']) public onMouseLeave(evt) {
      evt.preventDefault();
      evt.stopPropagation();
      this.background = '#eee';
    }
    //Arrastra archivo sobre el elemento
    @HostListener('dragover', ['$event']) public onDragOver(evt) {
      evt.preventDefault();
      evt.stopPropagation();
      this.background = '#999';
    }
  
    //El archivo aún es arrastrado, pero ya no sobre el elemento
    @HostListener('dragleave', ['$event']) public onDragLeave(evt) {
      evt.preventDefault();
      evt.stopPropagation();
      this.background = '#eee'
    }
  
    //Cuando se suelta el archivo sobre el elemento
    @HostListener('drop', ['$event']) public onDrop(evt) {
      evt.preventDefault();
      evt.stopPropagation();
      this.background = '#eee';
      let files = evt.dataTransfer.files;
      let valid_files: Array<File> = [];
      if (files.length > 0) {
        // Hay archivos...;
        for (let i = 0; i < files.length; i++) {
          // se itera dentro de los archivos
          const element = files[i];
          let ext = files[i].name.split('.')[files[i].name.split('.').length - 1]; //Obtiene la extensión
          // lasIndexOf devuelve -1 si no se halla el objeto dentro del array
          if (files[i].size > this.maxTam) {
            swal({
              type: 'error',
              title: 'Error',
              text: this.msgError
            });
            return;
          }else if (this.allowed_extensions.lastIndexOf(ext) != -1) {
            valid_files.push(files[i]);
          } else {
            swal({
              type: 'error',
              title: 'Error',
              text: this.msgAllowedExtensions
            });
            return;
          }
        }
        this.filesChangeEmiter.emit(valid_files);
      }
    }
  }