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

  getAllUpcomingByLoggedInBloodDonor(sortDirection?:string, sortProperty?:string): Observable<Appointment[]> {
    let path: string = `${this.config.appointments_url}/blood-donor/upcoming`;
    if(!!sortDirection || !!sortProperty) {
      const params = new URLSearchParams({
        sortDirection: sortDirection,
        sortProperty: sortProperty
      })
      path += "?" + params.toString();
    }
    console.log(path);
    return this.apiService.get(path);
  }

  uploadQrCode(Path:string) :Observable<any> {
    return this.apiService.post(`${this.config.appointments_url}/uploadQrCode/`, Path);
  }

  getAllPastByLoggedInBloodDonor(sortDirection?:string, sortProperty?:string): Observable<Appointment[]> {
    let path: string = `${this.config.appointments_url}/blood-donor/past`;
    if(!!sortDirection || !!sortProperty) {
      const params = new URLSearchParams({
        sortDirection: sortDirection,
        sortProperty: sortProperty
      })
      path += "?" + params.toString();
    }
    console.log(path);
    return this.apiService.get(path);
  }

  schedule(id: string): Observable<void> {
    return this.apiService.post(`${this.config.appointments_url}/schedule/${id}`, '');
  }

  cancel(id: string): Observable<void> {
    return this.apiService.post(`${this.config.appointments_url}/cancel/${id}`, '');
  }

  complete(id: string): Observable<void> {
    return this.apiService.post(`${this.config.appointments_url}/complete/${id}`, '');
  }

  getAppointmentsByMedicalStaffForTheTimePeriod(period : string, bank : string) : Observable<Appointment[]> {
    return this.apiService.post(`${this.config.appointments_url}/schedule/${period}/${bank}`,'');
  }

  setAsMissed(id: string): Observable<void> {
    return this.apiService.post(`${this.config.appointments_url}/missed/${id}`, '');
  }
}
