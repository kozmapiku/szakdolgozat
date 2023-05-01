import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {TokenStorageService} from "../service/token-storage.service";

@Injectable()
export class XhrInterceptorService implements HttpInterceptor{

  constructor(private token: TokenStorageService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.token.getToken();
    let xhr;
    if(token != null) {
       xhr = req.clone({
        headers: req.headers
          .set('X-Requested-With', 'XMLHttpRequest')
          .set('Authorization','Bearer' + token)
      });
    }
    else {
      xhr = req.clone({
        headers: req.headers
          .set('X-Requested-With', 'XMLHttpRequest')
          .set('Content-Type', 'application/json')
      });
    }

    return next.handle(xhr);
  }
}
