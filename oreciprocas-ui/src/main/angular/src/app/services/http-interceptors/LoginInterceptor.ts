
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpResponse, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { finalize, tap } from 'rxjs/operators';
import { SInicioSesionService } from '../s-inicio-sesion.service';
import { Observable } from 'rxjs';

@Injectable()
export class LoggingInterceptor implements HttpInterceptor {

    constructor(private sInicioSesion: SInicioSesionService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const startTime = Date.now();
        let status: string;
        if (this.sInicioSesion.tokenstr !== '' && this.sInicioSesion.tokenVencido !== true) {
            // Si logueado
            // Envío la solicitud al siguiente controlador, la clono y establezco el nuevo encabezado

            req = req.clone({
                headers: req.headers.set('Authorization', 'Bearer ' + this.sInicioSesion._ModToken.access_token)
            });

            console.log('%crequest:' + JSON.stringify(req), 'background: black;color:white;');

            return next.handle(req
                // .clone({
                //     headers: req.headers.set('Authorization', this.sInicioSesion._ModToken.access_token),
                // }) //Lo mismo de arriba pero en una sola línea
            ).pipe(
                tap(event => {
                    // console.log("%c"+JSON.stringify(event), 'background:white; color:black;');
                    if (event instanceof HttpErrorResponse) {
                    } else if (event instanceof HttpResponse) {
                    } else {
                    }

                },
                    (error: any) => {
                        console.error(JSON.stringify(error));
                    }
                ));

        } else {
            // console.log("Token: " + this.sInicioSesion.tokenstr, ", vencido: " + this.sInicioSesion.tokenVencido);
            // Si no, envía el request original
            // WARNING: PUEDE FALLAR, YA QUE UNA SOLA SOLICITUD HTTP PUEDE GENERAR MULTIPLES EVENTOS
            return next.handle(req).pipe(
                tap(
                    event => {
                        status = 'No definido.';
                        // Y se inspecciona para saber si es una instancia de HttpResponse
                        if (event instanceof HttpResponse) {
                            status = 'succeeded';
                            // Si existe un token y no está vencido, el usuario debe cambiar su estado a logueado
                            if (!this.sInicioSesion.tokenVencido) {
                                this.sInicioSesion.EsUsuarioLogueado.next(this.sInicioSesion.obtenerToken(event.body))
                            }
                        }
                    },
                    error => {
                        status = 'failed';
                        if ( (error.status === 0) || (error.error.error_description && error.error.error_description.includes('JDBC'))) {
                            error.error.error_description = 'Ha ocurrido un error, por favor intenta nuevamente más tarde.';
                            error.error.message = 'Ha ocurrido un error, por favor intenta nuevamente más tarde.';
                        }
                    }
                ),
                finalize(() => {
                    const elapsedTime = Date.now() - startTime;
                    const message = 'Método ' + req.method + ', con params: ' + req.urlWithParams
                        + ', y estado: ' + status + ', finalizó en ' + elapsedTime + 'ms.';
                    console.log(message);
                })
            );
        }
    }
}