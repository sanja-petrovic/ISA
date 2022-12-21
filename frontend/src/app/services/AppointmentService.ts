import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {config, Observable} from 'rxjs';
import {ApiService} from "./ApiService";
import {ConfigService} from "./ConfigService";
import {BloodBank} from "../model/BloodBank";
import {Appointment} from "../model/Appointment";

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(
    private apiService: ApiService,
    private config: ConfigService) {
  }

  getAll(): Observable<Appointment[]> {
    return this.apiService.get(`${this.config.appointments_url}`);
  }

  getAllByBloodBank(id: string): Observable<Appointment[]> {
    return this.apiService.get(`${this.config.appointments_url}/blood-bank/${id}`);
  }

  getAllAvailableByBloodBank(id: string): Observable<Appointment[]> {
    return this.apiService.get(`${this.config.appointments_url}/blood-bank/${id}/available`);
  }

  getAllByBloodDonor(id: string): Observable<Appointment[]> {
    return this.apiService.get(`${this.config.appointments_url}/blood-donor/${id}`);
  }

  getAllByLoggedInBloodDonor(): Observable<Appointment[]> {
    return this.apiService.get(`${this.config.appointments_url}/blood-donor/`);
  }

  getAllUpcomingByLoggedInBloodDonor(): Observable<Appointment[]> {
    return this.apiService.get(`${this.config.appointments_url}/blood-donor/upcoming`);
  }

  getAllPastByLoggedInBloodDonor(): Observable<Appointment[]> {
    return this.apiService.get(`${this.config.appointments_url}/blood-donor/past`);
  }
  getAllCancelledByLoggedInBloodDonor(): Observable<Appointment[]> {
    return this.apiService.get(`${this.config.appointments_url}/blood-donor/cancelled`);
  }

  getById(id: string): Observable<Appointment[]> {
    return this.apiService.get(`${this.config.appointments_url}/${id}`);
  }

  schedule(id: string): Observable<void> {
    return this.apiService.post(`${this.config.appointments_url}/schedule/${id}`, '');
  }

  cancel(id: string): Observable<void> {
    return this.apiService.post(`${this.config.appointments_url}/cancel/${id}`, '');
  }
}
