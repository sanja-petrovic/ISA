import {Injectable} from '@angular/core';
import {map} from 'rxjs/operators';
import {ApiService} from "./ApiService";
import {ConfigService} from "./ConfigService";
import { HttpClient } from '@angular/common/http';
import { MedicalStaff } from '../model/Users';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MedicalStaffService {

  currentUser: any;
  private readonly medicalStaffUrl = 'http://localhost:8080/api/medicalStaff/'

  constructor(
    private apiService: ApiService,
    private config: ConfigService,
    private http: HttpClient
  ) {
  }

  updateMedicalStaff (mediclStaff: MedicalStaff){
    return this.apiService.post(`${this.medicalStaffUrl}`, mediclStaff);
  }

}
