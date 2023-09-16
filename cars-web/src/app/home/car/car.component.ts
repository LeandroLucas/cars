import { Component, Input } from '@angular/core';
import { environment } from 'src/environments/environment';
import { PrivateService } from 'src/services/private.service';

@Component({
  selector: 'app-car',
  templateUrl: './car.component.html',
  styleUrls: ['./car.component.sass']
})
export class CarComponent {

  @Input() car!: any

  @Input() listener!: any

  loading: boolean = false

  errorMsg: string | null = null

  constructor(private privateService: PrivateService) { }

  edit() {
    this.listener?.onStartEditCar?.(this.car)
  }

  delete() {
    if (confirm(`Quer mesmo deletar o carro ${this.car.model} - ${this.car.licensePlate}`)) {
      this.loading = true
      this.privateService.deleteCar(this.car.id)
        .then(() => {
          this.listener?.onCarDeleted?.(this.car)
          this.loading = false
        }).catch(err => {
          console.error(err)
          this.loading = false
        })
    }
  }

  use() {
    this.privateService.getCar(this.car.id)
      .then(car => {
        alert(`Utilização contabilizada para ${this.car.model} - ${this.car.licensePlate}`)
      })
      .catch(err => {
        console.error(err)
      })
  }

  onCarImageSelected(event: any) {
    const selectedFile = event.target.files[0] as File;
    console.log(selectedFile)
    this.loading = true
    this.privateService.uploadCarImage(this.car.id, selectedFile)
      .then((car: any) => {
        this.loading = false
        this.errorMsg = null
        setTimeout(() => {
          this.car.imageName = car.imageName
        }, 1000)
      })
      .catch(err => {
        this.errorMsg = err.error.message
        this.loading = false
      })
  }

  getImgUrl(): string {
    return `${environment.imagesUrl}${this.car.imageName}`
  }
}
