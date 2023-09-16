import { Component } from '@angular/core';
import { PrivateService } from 'src/services/private.service';

@Component({
  selector: 'app-car-editor',
  templateUrl: './car-editor.component.html',
  styleUrls: ['./car-editor.component.sass']
})
export class CarEditorComponent {

  private _open: boolean = false

  protected loading: boolean = false
  protected editing: boolean = false

  protected errorMsg: string | null = null

  protected car: any = {}

  listener: any

  constructor(private privateService: PrivateService) { }

  get isOpen(): boolean {
    return this._open
  }

  open(car: any) {
    if (!car) {
      this.car = {}
      this.editing = false
    } else {
      this.editing = true
      this.car = { ...car }
    }
    this._open = true
  }

  close() {
    this.car = null
    this._open = false
  }

  confirm() {
    if (this.editing) { //editar
      this.privateService.updateCar(this.car)
        .then(() => {
          this.listener?.onCarUpdated?.(this.car)
          this.close()
          this.loading = false
          this.errorMsg = null
        })
        .catch(response => {
          this.errorMsg = response?.error?.message
          this.loading = false
        })
    } else { // criar
      this.privateService.createCar(this.car)
        .then((car: any) => {
          this.listener?.onCarCreated?.(car)
          this.close()
          this.loading = false
          this.errorMsg = null
        }).catch(response => {
          this.errorMsg = response?.error?.message
          this.loading = false
        })
    }
  }

  cancel() {
    this.close()
  }
}
