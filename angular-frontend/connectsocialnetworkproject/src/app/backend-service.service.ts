import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BackendServiceService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

   login(user: any) {
    return this.http.post(this.apiUrl + '/user/login', user);
  }
  
}
