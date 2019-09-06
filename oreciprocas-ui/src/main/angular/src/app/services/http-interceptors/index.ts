/* Http Interceptors */
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoggingInterceptor } from './LoginInterceptor';

/** Http interceptor providers en orden */
export const httpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: LoggingInterceptor, multi: true },
];