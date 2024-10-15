import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class NumberingService {
    private apiUrl = 'http://localhost:8080/atexo/api/numbering/generate';

    constructor(private http: HttpClient) {
    }

    generateNumber(userData: any): Observable<string> {
        return this.http.post<string>(this.apiUrl, userData, {responseType: 'text' as 'json'}).pipe(
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
