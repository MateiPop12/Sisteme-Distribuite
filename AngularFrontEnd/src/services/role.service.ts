import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Role} from "../models/role";

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  private baseUrl = 'http://localhost:8080/role';

  constructor(private http: HttpClient) { }

  getAllRoles(): Observable<Role[]> {
    const url = `${this.baseUrl}/all`;
    return this.http.get<Role[]>(url);
  }
}
