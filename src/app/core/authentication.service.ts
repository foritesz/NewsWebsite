import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {
    BehaviorSubject,
    catchError,
    map,
    Observable,
    of,
    tap
} from 'rxjs';

import { environment } from '../../environments/environment';

export interface LoginResponse {
    email: string;
}

export interface UserProfile {
    id: number;
    email: string;
    role: string;
}

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
    private readonly apiUrl = environment.apiUrl.replace(/\/$/, '');
    private readonly authenticated$ = new BehaviorSubject<boolean>(false);

    constructor(private http: HttpClient) {}

    authState(): Observable<boolean> {
        return this.authenticated$.asObservable();
    }

    refreshAuthState(): Observable<boolean> {
        return this.http
            .get<UserProfile>(`${this.apiUrl}/user/profile`)
            .pipe(
                tap(() => this.authenticated$.next(true)),
                map(() => true),
                catchError(() => {
                    this.authenticated$.next(false);
                    return of(false);
                })
            );
    }

    isAuthenticatedSnapshot(): boolean {
        return this.authenticated$.value;
    }

    login(email: string, password: string): Observable<LoginResponse> {
        return this.http
            .post<LoginResponse>(
                `${this.apiUrl}/authentication/login`,
                { email, password },
            )
            .pipe(tap(() => this.authenticated$.next(true)));
    }

    logout(): Observable<void> {
        return this.http
            .post<void>(
                `${this.apiUrl}/authentication/logout`,
                {}
            )
            .pipe(tap(() => this.authenticated$.next(false)));
    }
}
