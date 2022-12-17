import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PasswordDto, Patient } from '../model/Users';
import {ApiService} from "./ApiService";
import {ConfigService} from "./ConfigService";

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(
    private apiService: ApiService,
    private config: ConfigService
    ) { }

  getPatient(personalId : String) : Observable<Patient>{
    return this.apiService.get(`${this.config.blood_donors_url}/${personalId}`);
  }
  updatePatient(patientDto : Patient): Observable<any>{
    return this.apiService.put(`${this.config.blood_donors_url}/update`, patientDto);
  }

  updatePatientPassword(passwordDto : PasswordDto): Observable<any>{
    return this.apiService.put(`${this.config.users_url}/update`, passwordDto);
  }
}
