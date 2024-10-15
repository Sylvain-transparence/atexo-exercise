import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {BehaviorSubject, catchError, Observable, tap, throwError} from 'rxjs';
import {NumberingConfigurationDto} from "../models/numbering-models";

@Injectable({
  providedIn: 'root'
})
export class ConfigurationService {
  private apiUrl = 'http://localhost:8080/atexo/api/numbering/configuration';
  private currentConfigSubject = new BehaviorSubject<NumberingConfigurationDto | null>(null);

  constructor(private http: HttpClient) {
  }

  getCurrentConfig(): Observable<NumberingConfigurationDto | null> {
    return this.currentConfigSubject.asObservable();
  }

  getConfiguration(): Observable<NumberingConfigurationDto> {
    return this.http.get<NumberingConfigurationDto>(this.apiUrl).pipe(
      tap(config => this.currentConfigSubject.next(config)),
      catchError(this.handleError)
    );
  }

  saveConfiguration(config: NumberingConfigurationDto): Observable<any> {
    return this.http.post(this.apiUrl, config).pipe(
      tap(() => this.currentConfigSubject.next(config)),
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred!';
    if (error.error instanceof ErrorEvent) {
      // Client-side or network error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Backend error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
