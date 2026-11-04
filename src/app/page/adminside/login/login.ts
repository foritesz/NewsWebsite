import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { AuthenticationService } from '../../../core/authentication.service';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [CommonModule, FormsModule, RouterLink],
    templateUrl: './login.html',
    styleUrl: './login.css'
})
export class LoginComponent {
    email = '';
    password = '';
    errorMessage = '';
    isLoading = false;

    constructor(
        private auth: AuthenticationService,
        private router: Router,
        private route: ActivatedRoute,
    ) {}

    onSubmit(): void {
        this.isLoading = true;
        this.errorMessage = '';

        this.auth.login(this.email, this.password).subscribe({
            next: () => {
                const returnUrl = this.route.snapshot.queryParamMap.get('returnUrl');
                void this.router.navigateByUrl(returnUrl?.startsWith('/') ? returnUrl : '/author');
            },
            error: () => {
                this.errorMessage = 'Sikertelen bejelentkezés. Ellenőrizd az e-mail-címet és a jelszót.';
                this.isLoading = false;
            },
            complete: () => {
                this.isLoading = false;
            }
        });
    }
}
