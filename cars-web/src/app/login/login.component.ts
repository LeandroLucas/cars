import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent {

  loading: boolean = false
  errorMsg: string | null = null
  credentials: any = {}

  constructor(private authService: AuthService, private router: Router) { }

  login() {
    console.log(this.credentials)
    this.loading = true
    this.authService.login(this.credentials)
      .then(session => {
        this.errorMsg = null
        this.loading = false
        this.router.navigate(['home'])
      })
      .catch(response => {
        this.errorMsg = response.error.message
        this.loading = false
      })
  }
}
