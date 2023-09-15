import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from 'src/services/auth.service';

export function authGuard(): CanActivateFn {
    return () => {
        const authService: AuthService = inject(AuthService)
        const router: Router = inject(Router)
        if (!authService.isLoggedIn()) {
            router.navigate(['login'])
            return false
        }
        return true
    }
}

export function isOffline(): CanActivateFn {
    return () => {
        const authService: AuthService = inject(AuthService)
        const router: Router = inject(Router)
        if (authService.isLoggedIn()) {
            router.navigate(['home'])
            return false
        }
        return true
    }
}
