import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthService} from "./auth.service";
import {BehaviorSubject, Observable} from "rxjs";
import {User} from "../models/user";
import {CreateUser} from "../models/create-user";


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8080/user';

  constructor(private http: HttpClient, private authService: AuthService) { }

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  getAllUsers(): Observable<User[]> {
    const url = `${this.baseUrl}/all`;
    return this.http.get<User[]>(url, { headers: this.getAuthHeaders() });
  }

  getUserByUsername(username: any): Observable<User> {
    const url = `${this.baseUrl}/${username}`;
    return this.http.get<User>(url, { headers: this.getAuthHeaders() });
  }

  createUser(user: CreateUser): Observable<User> {
    const url = `${this.baseUrl}/create`;
    return this.http.post<User>(url, user, { headers: this.getAuthHeaders() });
  }

  updateUser(user: User): Observable<User> {
    const url = `${this.baseUrl}/update`;
    return this.http.put<User>(url, user, { headers: this.getAuthHeaders() });
  }

  deleteUser(id: number): Observable<void> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete<void>(url, { headers: this.getAuthHeaders() });
  }
}
