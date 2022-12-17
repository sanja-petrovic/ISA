import {Injectable} from '@angular/core';
import {map} from 'rxjs/operators';
import {ApiService} from "./ApiService";
import {ConfigService} from "./ConfigService";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MedicalStaff } from '../model/Users';
import { Observable } from 'rxjs';
import { BloodBank } from '../model/BloodBank';

@Injectable({
  providedIn: 'root'
})
export class MedicalStaffService {

  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(
    private apiService: ApiService,
    private config: ConfigService,
    private http: HttpClient
  ) {
  }

  updateMedicalStaff (medicalStaff: MedicalStaff){
    return this.apiService.post(this.config.medical_staff_url, medicalStaff);
  }
  registerMedicalStaff (medicalStaff: MedicalStaff){
    return this.apiService.post(`${this.config.medical_staff_url}/register`, medicalStaff);
  }
  getBloodBank (): Observable<BloodBank>{
    return this.apiService.get(`${this.config.medical_staff_url}/bank`);
  }

}
