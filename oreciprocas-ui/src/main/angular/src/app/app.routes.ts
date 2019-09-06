// routerhandler
import { RouterModule, Routes } from '@angular/router';
// Guards
import { GVistasInternas } from './services/guards/GVistasInternas';
// components
import { CInicioSesionComponent } from './components/c-inicio-sesion/c-inicio-sesion.component';
import { CDetalleORComponent } from './components/c-detalle-or/c-detalle-or.component';
import { CEnvioMensajeComponent } from './components/c-detalle-or/c-envio-mensaje/c-envio-mensaje.component';
import { CRegistroComponent } from './components/c-registro/c-registro.component';
import { CTransaccionesComponent } from './components/c-transacciones/c-transacciones.component';
import { CCambioContrasenaComponent } from './components/c-cambio-contrasena/c-cambio-contrasena.component';
import { CPerfilComponent } from './components/administracion/cperfil/cperfil.component';
import { CCargarTransaccionComponent} from './components/c-cargar-transaccion/c-cargar-transaccion.component';
import { CPlantillaComponent} from './components/c-plantilla/c-plantilla.component';

const APP_ROUTES: Routes = [
  {
    path: 'InicioSesion',
    component: CInicioSesionComponent,
    data: {
      breadcrumb: 'Inicio de sesión' // texto de miga de pan
    }
  },
  {
    path: 'InicioSesionFNA',
    component: CInicioSesionComponent,
    data: {
      breadcrumb: 'Inicio de sesión'
    }
  },
  {
    path: 'Registro',
    component: CRegistroComponent,
    data: {
      breadcrumb: 'Registro'
    }
  },
  {
    path: 'CambioContrasena',
    component: CCambioContrasenaComponent,
    data: {
      breadcrumb: 'Cambio de contraseña'
    }
  },
  {
    path: 'CambioContrasena/:param',
    component: CCambioContrasenaComponent,
    data: {
      breadcrumb: 'Administración›Cambio de contraseña'
    }
  },
  {
    path: 'Transacciones',
    component: CTransaccionesComponent,
    canActivate: [GVistasInternas],
    data: {
      animation: 'Transacciones',
      breadcrumb: 'Transacciones›Consultar'
    },
  },
  {
    path: 'Transacciones/Detalle',
    component: CDetalleORComponent,
    canActivate: [GVistasInternas],
    data: {
      animation: 'Detalle',
      breadcrumb: 'Transacciones›Detalle'
    }
  },
  {
    path: 'Perfil',
    component: CPerfilComponent,
    canActivate: [GVistasInternas],
    data: {
      animation: 'Perfil',
      breadcrumb: 'Administración›Editar entidad'
    }
  },
  {
    path: 'PerfilGestion',
    component: CPerfilComponent,
    canActivate: [GVistasInternas],
    data: {
      animation: 'Perfil',
      breadcrumb: 'Administración›Gestionar entidades'
    }
  },
  {
    path: 'EnvioMensaje',
    component: CEnvioMensajeComponent,
    canActivate: [GVistasInternas],
    data: {
      breadcrumb: 'EnvioMensaje'
    }
  },
  {
    path: 'CargarArchivo',
    component: CCargarTransaccionComponent,
    canActivate: [GVistasInternas],
    data: {
      animation: 'CargarArchivo',
      breadcrumb: 'Transacciones›Cargar archivo'
    },
  },
  {
    path: 'Plantilla',
    component: CPlantillaComponent,
    canActivate: [GVistasInternas],
    data: {
      animation: 'Plantilla',
      breadcrumb: 'Administración›Gestionar plantillas'
    },
  },
  {
    path: '**',
    redirectTo: '/InicioSesion'
  },

];

export const APP_ROUTING = RouterModule.forRoot(APP_ROUTES);
