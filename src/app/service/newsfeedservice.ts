import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { Article } from '../model/article';


@Injectable({ providedIn: 'root' })
export class NewsFeedService {
  private readonly apiUrl = 'http://localhost:8080/api/news';

  constructor(private readonly http: HttpClient) {}

  getNewsId(id: number): Observable<Article> {
    return this.http.get<Article>(`${this.apiUrl}/${id}`).pipe(
      catchError(error => this.handleError(error))
    );
  }
  getWeeklyNews(): Observable<Article[]> {
    return this.http.get<Article[]>(`${this.apiUrl}/weekly`).pipe(
      catchError(error => this.handleError(error))
    );
  }

  getFreshNews(): Observable<Article[]> {
    return this.http.get<Article[]>(`${this.apiUrl}/fresh`).pipe(
      catchError(error => this.handleError(error))
    );
  }

  getPopularNews(): Observable<Article[]> {
    return this.http.get<Article[]>(`${this.apiUrl}/popular`).pipe(
      catchError(error => this.handleError(error))
    );
  }

    private handleError(error: HttpErrorResponse): Observable<never> {
    let message = 'An unknown error occurred';
    if (error.error instanceof ErrorEvent) {
      message = error.error.message;
    } else {
      message = error.error?.message || `Error Code: ${error.status}`;
    }
    return throwError(() => new Error(message));
  }
}
