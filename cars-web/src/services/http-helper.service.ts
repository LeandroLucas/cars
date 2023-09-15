import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable, of as observableOf } from 'rxjs';
import { StorageService } from './storage.service';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class HttpHelperService {

  constructor(private storageService: StorageService) { }

  public getHttpOptions(headers?: any): any {
    let httpHeaders: HttpHeaders;
    if (headers) {
      httpHeaders = new HttpHeaders(headers);
    } else {
      httpHeaders = new HttpHeaders({ Authorization: this.storageService.get(AuthService.TOKEN_KEY)!! });
    }
    const httpOptions = {
      headers: httpHeaders
    };
    return httpOptions;
  }

  public getEmptyHttpOptions(): any {
    const httpOptions = {
      headers: []
    };
    return httpOptions;
  }

  public handleError(operation = 'operation', response: any): Observable<any> {
    console.error(operation + ' error: ', response);
    return observableOf(response);
  }
}
