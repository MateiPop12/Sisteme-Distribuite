import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Reading} from "../models/reading";

@Injectable({
  providedIn: 'root'
})
export class MonitorService {

  private baseUrl = 'http://monitoring.localhost/reading';
  // private baseUrl = 'http://localhost:8082/reading';

  constructor(private http: HttpClient) { }

  getReadingsByDeviceId(deviceId: number): Observable<Reading[]> {
    const url = `${this.baseUrl}/all/${deviceId}`;
    return this.http.get<Reading[]>(url);
  }
}
