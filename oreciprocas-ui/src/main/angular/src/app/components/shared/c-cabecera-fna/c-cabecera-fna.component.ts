import { Component, OnInit, ElementRef, ViewChild, AfterViewInit } from '@angular/core';

declare var $;

@Component({
  selector: 'app-c-cabecera-fna',
  templateUrl: './c-cabecera-fna.component.html',
  styleUrls: ['./c-cabecera-fna.component.css']
})
export class CCabeceraFNAComponent implements AfterViewInit {
  private ELEMENTS_URL = '/FNA-TemplateUI';

  @ViewChild('header')
  header: ElementRef;

  constructor() {
  }

  ngAfterViewInit() {
    $.get( /*'https://fnabogpsoa.fna.gov.co:8099' +*/ this.ELEMENTS_URL + '/fna/header.xhtml', html => {
      this.header.nativeElement.innerHTML = html;
      const script = document.createElement('script');
      script.type = 'text/javascript';
      script.src = './assets/util-header.js';
      document.getElementsByTagName('head')[0].appendChild(script);
      // Soluciona lo del tab
      document.getElementById('contraste').setAttribute('tabindex', '0');

    });
  }
}
