import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from 'src/services/user.service';
import { ModalDirective } from '../ModalDirective';
import { UserEditorComponent } from './user-editor/user-editor.component';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.sass']
})
export class UsersComponent implements OnInit {

  @ViewChild(ModalDirective, { static: true }) modalHost!: ModalDirective

  users: any[] = []

  userEditor: UserEditorComponent | null = null

  eventsListener: any = {
    onDelete: (deletedUser: any) => {
      this.users = this.users.filter(user => user.id != deletedUser.id)
    },
    onStartEditUser: (user: any) => {
      this.edit(user)
    },
    onUserCreated: (user: any) => {
      this.users.push(user)
    },
    onUserUpdated: (updatedUser: any) => {
      const index = this.users.findIndex((user: any) => user.id == updatedUser.id)
      this.users[index] = updatedUser
    }
  }

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    const viewContainerRef = this.modalHost.viewContainerRef
    viewContainerRef.clear()

    this.userEditor = viewContainerRef.createComponent<UserEditorComponent>(UserEditorComponent).instance
    this.userEditor.listener = this.eventsListener

    this.listUsers()
  }

  listUsers() {
    this.userService.list().then(users => {
      this.users = users
    }).catch(err => {
      console.error(err)
    })
  }

  create() {
    this.userEditor?.open(null)
  }

  edit(user: any) {
    this.userEditor?.open(user)
  }


}
