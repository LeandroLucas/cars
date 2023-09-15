import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/services/auth.service';
import { PrivateService } from 'src/services/private.service';
import { ModalDirective } from '../ModalDirective';
import { CarEditorComponent } from './car-editor/car-editor.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass']
})
export class HomeComponent implements OnInit {

  @ViewChild(ModalDirective, { static: true }) modalHost!: ModalDirective

  loading: boolean = false

  me: any = {}

  private carEditor: CarEditorComponent | null = null

  eventsListener: any = {
    onCarDeleted: (deletedCar: any) => {
      this.me.cars = this.me.cars.filter((car: any) => car.id != deletedCar.id)
    },
    onStartEditCar: (car: any) => {
      this.carEditor?.open(car)
    },
    onCarCreated: (car: any) => {
      this.me.cars.push(car)
    },
    onCarUpdated: (updatedUser: any) => {
      const index = this.me.cars.findIndex((user: any) => user.id == updatedUser.id)
      this.me.cars[index] = updatedUser
    }
  }

  constructor(private authService: AuthService,
    private privateService: PrivateService,
    private router: Router) {
  }

  ngOnInit(): void {
    const viewContainerRef = this.modalHost.viewContainerRef
    viewContainerRef.clear()

    this.carEditor = viewContainerRef.createComponent<CarEditorComponent>(CarEditorComponent).instance
    this.carEditor.listener = this.eventsListener

    this.loading = true
    this.privateService.me()
      .then(me => {
        this.me = me
        this.loading = false
      })
      .catch(err => {
        alert('Problema ao carregar dados do usuário logado')
        console.error(err)
      })
  }

  logout() {
    this.loading = true
    this.authService.logout().then(() => {
      this.loading = false
      this.router.navigate(['login'])
    }).catch(response => {
      console.error(response)
      this.loading = false
    })
  }

  createCar() {
    this.carEditor?.open(null)
  }

  listCars() {
    this.loading = true
    this.privateService.listCars()
      .then(cars => {
        this.me.cars = cars
        this.loading = false
      })
      .catch(response => {
        console.error(response)
        this.loading = false
      })
  }
}
