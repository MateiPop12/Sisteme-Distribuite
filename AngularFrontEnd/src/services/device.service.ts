import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthService} from "./auth.service";
import {Observable} from "rxjs";
import {Device} from "../models/device";
import {DeviceUser} from "../models/device-user";

@Injectable({
  providedIn: 'root'
})
export class DeviceService {

  private baseUrl = 'http://localhost:8081/device';

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  getAllDevices(): Observable<Device[]> {
    const url = `${this.baseUrl}/all`;
    return this.http.get<Device[]>(url);
  }

  getDevicesByUser(user: DeviceUser): Observable<Device[]> {
    const url = `${this.baseUrl}/all/${user.id}`;
    return this.http.get<Device[]>(url);
  }

  createDevice(deviceDto: Device): Observable<Device> {
    const url = `${this.baseUrl}/create`;
    console.log(deviceDto);
    return this.http.post<Device>(url, deviceDto);
  }

  updateDevice(deviceDto: Device): Observable<Device> {
    const url = `${this.baseUrl}/update`;
    return this.http.put<Device>(url, deviceDto);
  }

  deleteDevice(id: number): Observable<void> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete<void>(url);
  }
}
