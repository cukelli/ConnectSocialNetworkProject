import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from './user.model';
import { RegistrationUser } from './registration-user';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { ChangePassword } from './change-password';
import jwt_decode from "jwt-decode";

@Injectable({
  providedIn: 'root'
})

export class BackendServiceService {
  private apiUrl = 'http://localhost:8080';
  public userDetails!: RegistrationUser; 


  constructor(private http: HttpClient) { }

   login(user: User) {
    return this.http.post(this.apiUrl + '/user/login', user);
  }

  getDecodedToken(token: string) {
  return jwt_decode(token) as { username: string };
}

  registration(user: RegistrationUser) {
  return this.http.post(this.apiUrl + '/user/registration', user);
  }

  getUserPosts(username: string) {

    let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  })

  let requestOptions = { headers: headers };  
return this.http.get(this.apiUrl + '/post/user', requestOptions);

  }

  getUser(): Observable<RegistrationUser> {
  let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  })

  let requestOptions = { headers: headers };
  return this.http.get<RegistrationUser>(this.apiUrl + '/user/', requestOptions);
}

  changePasswordUser(changePassword: ChangePassword): Observable<JSON> {
  let headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  })

  let requestOptions = { headers: headers };
  return this.http.post<JSON>(this.apiUrl + '/user/changePassword',changePassword,requestOptions);
}
}
