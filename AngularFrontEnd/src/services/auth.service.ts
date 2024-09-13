import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, tap} from 'rxjs';
import {LoginUser} from "../models/loginUser";
import {RegisterUser} from "../models/registerUser";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = "http://localhost:8080/auth";
  private tokenKey = 'authToken'

  constructor(private http: HttpClient) { }

  login(loginUser:LoginUser): Observable<any> {
    const url = `${this.baseUrl}/authenticate`;

    return this.http.post(url, loginUser).pipe(
      tap((response: any) => {
        this.storeToken(response.token);
      })
    );
  }

  register(registerUser:RegisterUser): Observable<any> {
    const url = `${this.baseUrl}/register`;

    return this.http.post(url, registerUser).pipe(
      tap((response: any) => {
        this.storeToken(response.token);
      })
    );
  }

  private storeToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
  }
}
