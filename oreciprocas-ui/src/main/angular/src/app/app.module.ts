import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HashLocationStrategy, LocationStrategy, registerLocaleData } from '@angular/common';
// Main component
import { AppComponent } from './app.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
// App Routes
import { APP_ROUTING } from './app.routes';
// Services
import { SInicioSesionService } from './services/s-inicio-sesion.service';
import { SRegistroService } from './services/s-registro.service';
import { SCambioContrasenaService } from './services/s-cambio-contrasena.service';
import { SEnvioMensajeService } from './services/s-envio-mensaje.service';
import { SOperacionesReciprocasService } from './services/s-operaciones-reciprocas.service';
import { SEdicionPerfilService } from './services/s-edicion-perfil.service';
// Guards
import { GVistasInternas } from './services/guards/GVistasInternas';
// HTTPService
import { HttpClientModule } from '@angular/common/http';
// Spinner
import { NgxSpinnerModule } from 'ngx-spinner';
// Components compartidos
import { CMenuComponent } from './components/shared/c-menu/c-menu.component';
import { CCabeceraFNAComponent } from './components/shared/c-cabecera-fna/c-cabecera-fna.component';
import { CPiePaginaFNAComponent } from './components/shared/c-pie-pagina-fna/c-pie-pagina-fna.component';
import { CMigaPanComponent } from './components/shared/c-miga-pan/c-miga-pan.component';
// Components admin
import { CPerfilComponent } from './components/administracion/cperfil/cperfil.component';
// CORS
import { CustExtBrowserXhr } from './utils/cust-ext-browser-xhr';
import { BrowserXhr } from '@angular/http';
// Interceptors
import { httpInterceptorProviders } from './services/http-interceptors';
// Directives
import { DragnDropDirective } from './services/directives/dragndrop.directive';
// Components
import { CInicioSesionComponent } from './components/c-inicio-sesion/c-inicio-sesion.component';
import { CRegistroComponent } from './components/c-registro/c-registro.component';
import { CCambioContrasenaComponent } from './components/c-cambio-contrasena/c-cambio-contrasena.component';
import { CTransaccionesComponent } from './components/c-transacciones/c-transacciones.component';
import { CDetalleORComponent } from './components/c-detalle-or/c-detalle-or.component';
import { CEnvioMensajeComponent } from './components/c-detalle-or/c-envio-mensaje/c-envio-mensaje.component';
import { CComentariosComponent } from './components/c-detalle-or/c-comentarios/c-comentarios.component';
import { CEstadoComponent } from './components/c-detalle-or/c-estado/c-estado.component';
import { CAdjuntosComponent } from './components/c-detalle-or/c-adjuntos/c-adjuntos.component';
// import { PipeSeguro } from './components/pipes/PipeSeguro';
// Animations
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
//Directives
import { OnlyNumber } from './services/directives/OnlyNumber.directive';
import { CCargarTransaccionComponent } from './components/c-cargar-transaccion/c-cargar-transaccion.component';
//Dialogs
import { SweetAlert2Module } from '@toverux/ngx-sweetalert2';
import { CPlantillaComponent } from './components/c-plantilla/c-plantilla.component';
//Pipe
import { BooleanConvencion } from './components/pipes/pipeBoolean';
import { CBannerComponent } from './components/shared/c-banner/c-banner.component';
// boostrap
import { NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';
import { TypeaheadComponent } from './components/typeahead/typeahead.component';

@NgModule({
  declarations: [
    AppComponent,
    CMenuComponent,
    CInicioSesionComponent,
    CRegistroComponent,
    CDetalleORComponent,
    CTransaccionesComponent,
    CEnvioMensajeComponent,
    CComentariosComponent,
    CEstadoComponent,
    CAdjuntosComponent,
    CCabeceraFNAComponent,
    CPiePaginaFNAComponent,
    CMigaPanComponent,
    CCambioContrasenaComponent,
    // PipeSeguro,
    DragnDropDirective,
    CPerfilComponent,
    OnlyNumber,
    CCargarTransaccionComponent,
    CPlantillaComponent,
    BooleanConvencion,
    CBannerComponent,
    TypeaheadComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    APP_ROUTING,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    NgxSpinnerModule,
    SweetAlert2Module.forRoot({
      buttonsStyling: false,
      customClass: 'modal-content',
      confirmButtonClass: 'btn btn-primary',
      cancelButtonClass: 'btn'
    }),
    NgbTypeaheadModule
  ],
  providers: [
    {
      provide: LocationStrategy,
      useClass: HashLocationStrategy
    },
    { provide: BrowserXhr, useClass: CustExtBrowserXhr },
    SInicioSesionService,
    SRegistroService,
    SCambioContrasenaService,
    SEnvioMensajeService,
    GVistasInternas,
    httpInterceptorProviders,
    SOperacionesReciprocasService,
    SEdicionPerfilService,
    AppComponent,
    CComentariosComponent
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
