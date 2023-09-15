import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, firstValueFrom } from 'rxjs';
import { HttpHelperService } from './http-helper.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = 'http://127.0.0.1:80/api'
  constructor(private http: HttpClient,
    private httpHelperService: HttpHelperService) { }

  list(): Promise<any[]> {
    const obs: Observable<any> = this.http.get(`${this.url}/users`,
      this.httpHelperService.getEmptyHttpOptions())
    return firstValueFrom(obs)
      .catch(err => {
        this.httpHelperService.handleError('list-users', err)
        throw err
      })
  }

  delete(id: number): Promise<any[]> {
    const obs: Observable<any> = this.http.delete(`${this.url}/users/${id}`,
      this.httpHelperService.getEmptyHttpOptions())
    return firstValueFrom(obs)
      .catch(err => {
        this.httpHelperService.handleError('delete-user', err)
        throw err
      })
  }

  create(user: any): Promise<any[]> {
    const obs: Observable<any> = this.http.post(`${this.url}/users`,
      user,
      this.httpHelperService.getEmptyHttpOptions())
    return firstValueFrom(obs)
      .catch(err => {
        this.httpHelperService.handleError('create-user', err)
        throw err
      })
  }

  get(id: number): Promise<any> {
    const obs: Observable<any> = this.http.get(`${this.url}/users/${id}`,
      this.httpHelperService.getEmptyHttpOptions())
    return firstValueFrom(obs)
      .catch(err => {
        this.httpHelperService.handleError('get-user', err)
        throw err
      })
  }

  update(user: any): Promise<any> {
    const obs: Observable<any> = this.http.put(`${this.url}/users/${user.id}`,
      user,
      this.httpHelperService.getEmptyHttpOptions())
    return firstValueFrom(obs)
      .catch(err => {
        this.httpHelperService.handleError('get-user', err)
        throw err
      })
  }
}
