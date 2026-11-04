import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpInterceptorFn,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

/** Sends the HttpOnly authentication cookie only when the request may need authentication. */
export const credentialsInterceptor: HttpInterceptorFn = (request, next) => {
  const isPublicNewsFeed = /\/api\/news\/(weekly|fresh|popular)$/.test(request.url);
  return next(request.clone({ withCredentials: !isPublicNewsFeed }));
};

/** Compatibility for the legacy NgModule registration in register.ts. */
@Injectable()
export class HttpInterceptorService implements HttpInterceptor {
  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const isPublicNewsFeed = /\/api\/news\/(weekly|fresh|popular)$/.test(request.url);
    return next.handle(request.clone({ withCredentials: !isPublicNewsFeed }));
  }
}
