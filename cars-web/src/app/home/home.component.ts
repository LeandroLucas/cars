import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass']
})
export class HomeComponent {

  loading: boolean = false

  constructor(private authService: AuthService,
    private router: Router) {
  }

  logout() {
    this.loading = true
    this.authService.logout().then(() => {
      this.loading = false
      this.router.navigate(['login'])
    }).catch(response => {
      console.error(response)
    })
  }
}
