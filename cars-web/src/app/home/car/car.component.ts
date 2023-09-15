import { Component, Input } from '@angular/core';
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
}
