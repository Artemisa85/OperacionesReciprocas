import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
declare var $;
@Component({
  selector: 'app-c-pie-pagina-fna',
  templateUrl: './c-pie-pagina-fna.component.html',
  styleUrls: ['./c-pie-pagina-fna.component.css']
})
export class CPiePaginaFNAComponent implements AfterViewInit {

  private ELEMENTS_URL = '/FNA-TemplateUI';

  @ViewChild('footer')
  footer: ElementRef;

  footerHTML = '';

  constructor() { }

  ngAfterViewInit() {
    $.get( /*'https://fnabogpsoa.fna.gov.co:8099' +*/ this.ELEMENTS_URL + '/fna/footer.xhtml', html => {
      const { nativeElement } = this.footer;

      nativeElement.innerHTML = html;

      this.agregarUltimaActualizacion();
    });
  }

  agregarUltimaActualizacion() {
    const dows = new Array('Domingo', 'Lunes', 'Martes',
      'Miércoles', 'Jueves', 'Viernes', 'Sábado');
    const months = new Array('enero', 'febrero', 'marzo',
      'abril', 'mayo', 'junio', 'julio', 'agosto',
      'septiembre', 'octubre', 'noviembre',
      'diciembre');
    const now = new Date();
    const dow = now.getDay();
    const d = now.getDate();
    const m = now.getMonth();
    const h = now.getTime();
    const y = now.getFullYear();
    document.getElementById('fecha').innerHTML = d + ' de '
      + months[m] + ' de ' + y;
    document.getElementById('date_02').innerHTML = d + ' de '
      + months[m] + ' de ' + y;
    document.getElementById('year_01').innerHTML = y + '';
    document.getElementById('year_02').innerHTML = y + '';
  }


}
