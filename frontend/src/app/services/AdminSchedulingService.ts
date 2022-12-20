import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './ApiService';
import { ConfigService } from './ConfigService';

@Injectable({
  providedIn: 'root'
})
export class AdminSchedulingService {

  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  constructor(
    private apiService: ApiService,
    private config: ConfigService) { }

  createAppointment(appointment: any): Observable<any> {
    return this.apiService.post(`${this.config.admin_scheduling_url}`, appointment);
  }
}
