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

  currentUser: any;
  private readonly medicalStaffUrl = 'http://localhost:8080/api/medicalStaff/'
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(
    private apiService: ApiService,
    private config: ConfigService,
    private http: HttpClient
  ) {
  }

  updateMedicalStaff (mediclStaff: MedicalStaff){
    return this.apiService.post(`${this.medicalStaffUrl}`, mediclStaff);
  }
  registerMedicalStaff (medicalStaff: MedicalStaff){
    return this.apiService.post(this.medicalStaffUrl+'register', medicalStaff);
  }
  getBloodBank (): Observable<BloodBank>{
    return this.http.get<BloodBank>(this.medicalStaffUrl + 'bank',{headers :this.headers});
  }

}
