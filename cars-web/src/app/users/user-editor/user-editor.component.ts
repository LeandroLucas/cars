import { Component, Input } from '@angular/core';
import { MatNativeDateModule } from '@angular/material/core';
import { UserService } from 'src/services/user.service';

@Component({
  selector: 'app-user-editor',
  templateUrl: './user-editor.component.html',
  styleUrls: ['./user-editor.component.sass']
})
export class UserEditorComponent {

  prickerStartDate = new Date(1990, 0, 1);

  private _open: boolean = false

  editing: boolean = false

  protected user: any

  listener: any

  errorMsg: string | null = null

  loading: boolean = false

  constructor(private userService: UserService) {

  }

  cancel() {
    this.close()
  }

  get isOpen(): boolean {
    return this._open
  }

  open(user: any) {
    if (!user) {
      this.user = {}
      this.editing = false
    } else {
      this.loading = true
      this.user = user
      this.userService.get(user.id)
        .then(user => {
          this.loading = false
          user.birthday = this.buildDate(user.birthday)
          this.user = user
          this.editing = true
        }).catch(response => {
          this.errorMsg = response.error.message
          this.loading = false
          this.editing = true
        })
    }
    this._open = true
  }

  close() {
    this.user = null
    this._open = false
  }

  confirm() {
    this.loading = true
    this.user.birthday = this.formatDate(this.user.birthday)
    if (this.editing) { //editar usuário
      this.userService.update(this.user)
        .then(() => {
          this.listener?.onUserUpdated?.(this.user)
          this.close()
          this.loading = false
        }).catch(response => {
          this.errorMsg = response.error.message
          this.loading = false
        })
    } else { //criar usuário
      this.userService.create(this.user)
        .then((user: any) => {
          this.listener?.onUserCreated?.(user)
          this.close()
          this.loading = false
        }).catch(response => {
          this.errorMsg = response.error.message
          this.loading = false
        })
    }
  }

  private formatDate(date: Date) {
    const year = date.getFullYear()
    const month = String(date.getMonth() + 1).padStart(2, '0')
    const day = String(date.getDate()).padStart(2, '0')

    return `${year}-${month}-${day}`
  }

  private buildDate(date: string): Date {
    const split = date.split('-');
    if (split.length === 3) {
      const year = parseInt(split[0], 10);
      const month = parseInt(split[1], 10) - 1; // O mês começa em 0 (janeiro) no objeto Date
      const day = parseInt(split[2], 10);

      return new Date(year, month, day);
    } else {
      throw new Error('Formato de data inválido. Use YYYY-MM-DD.');
    }
  }
}
