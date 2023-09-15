import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpHelperService } from './http-helper.service';
import { Observable, firstValueFrom } from 'rxjs';
import { StorageService } from './storage.service';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public static TOKEN_KEY = "token"

  private url = environment.apiUrl

  constructor(private http: HttpClient,
    private httpHelperService: HttpHelperService,
    private storageService: StorageService) { }

  login(credentials: any): Promise<any[]> {
    const obs: Observable<any> = this.http.post(`${this.url}/signin`,
      credentials,
      this.httpHelperService.getEmptyHttpOptions())
    return firstValueFrom(obs)
      .then(session => {
        this.storageService.save(AuthService.TOKEN_KEY, session.token)
        return session
      })
      .catch(err => {
        this.httpHelperService.handleError('login', err)
        throw err
      })
  }

  logout(): Promise<void> {
    const obs: Observable<any> = this.http.delete(`${this.url}/signout`,
      this.httpHelperService.getHttpOptions())
    return firstValueFrom(obs)
      .then(() => {
        this.storageService.remove(AuthService.TOKEN_KEY)
      })
      .catch(err => {
        this.httpHelperService.handleError('logout', err)
        throw err
      })
  }

  isLoggedIn(): boolean {
    return this.storageService.get(AuthService.TOKEN_KEY) != null
  }
}
