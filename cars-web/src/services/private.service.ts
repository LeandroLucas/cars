import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpHelperService } from './http-helper.service';
import { Observable, firstValueFrom } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PrivateService {

  private url = environment.apiUrl

  constructor(private http: HttpClient,
    private httpHelperService: HttpHelperService) { }

  me(): Promise<any> {
    const obs: Observable<any> = this.http.get(`${this.url}/me`,
      this.httpHelperService.getHttpOptions())
    return firstValueFrom(obs)
      .catch(err => {
        this.httpHelperService.handleError('me', err)
        throw err
      })
  }

  deleteCar(carId: number): Promise<void> {
    const obs: Observable<any> = this.http.delete(`${this.url}/cars/${carId}`,
      this.httpHelperService.getHttpOptions())
    return firstValueFrom(obs)
      .catch(err => {
        this.httpHelperService.handleError('delete-car', err)
        throw err
      })
  }

  getCar(carId: number): Promise<any> {
    const obs: Observable<any> = this.http.get(`${this.url}/cars/${carId}`,
      this.httpHelperService.getHttpOptions())
    return firstValueFrom(obs)
      .catch(err => {
        this.httpHelperService.handleError('get-car', err)
        throw err
      })
  }

  createCar(car: any): Promise<any> {
    const obs: Observable<any> = this.http.post(`${this.url}/cars`,
      car,
      this.httpHelperService.getHttpOptions())
    return firstValueFrom(obs)
      .catch(err => {
        this.httpHelperService.handleError('create-car', err)
        throw err
      })
  }

  updateCar(car: any): Promise<any> {
    const obs: Observable<any> = this.http.put(`${this.url}/cars/${car.id}`,
      car,
      this.httpHelperService.getHttpOptions())
    return firstValueFrom(obs)
      .catch(err => {
        this.httpHelperService.handleError('update-car', err)
        throw err
      })
  }

  listCars(): Promise<any[]> {
    const obs: Observable<any> = this.http.get(`${this.url}/cars`,
      this.httpHelperService.getHttpOptions())
    return firstValueFrom(obs)
      .catch(err => {
        this.httpHelperService.handleError('list-cars', err)
        throw err
      })
  }

  uploadCarImage(carId: number, file: File) {
    const formData = new FormData();
    formData.append('file', file);
    const obs: Observable<any> = this.http.post(`${this.url}/cars/${carId}/img`,
      formData,
      this.httpHelperService.getHttpOptions())
    return firstValueFrom(obs)
      .catch(err => {
        this.httpHelperService.handleError('upload-car-image', err)
        throw err
      })
  }
}
