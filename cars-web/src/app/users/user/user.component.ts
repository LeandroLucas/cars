import { Component, Input } from '@angular/core';
import { UserService } from 'src/services/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.sass']
})
export class UserComponent {

  @Input() user!: any

  @Input() listener!: any

  carsPanelOpen: boolean = false
  carsLoading: boolean = false

  constructor(private userService: UserService) {

  }

  delete(user: any) {
    if (confirm(`Quer mesmo deletar o usuário ${user.firstName}?`)) {
      this.userService.delete(user.id)
        .then(() => {
          this.listener?.onDelete(user)
        })
        .catch((err) => {
          console.error(err)
        })
    }
  }

  edit(user: any) {
    this.listener?.onStartEditUser(user)
  }

  togglePannel() {
    if (this.carsPanelOpen && this.user.cars == null) {
      this.carsLoading = true
      this.userService.get(this.user.id)
        .then((user: any) => {
          this.user = user
          this.carsLoading = false
        }).catch(response => {
          this.carsLoading = false
        })
    }
  }
}
