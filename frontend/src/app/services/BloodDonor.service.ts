import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PasswordDto, BloodDonor } from '../model/Users';
import {ApiService} from "./ApiService";
import {ConfigService} from "./ConfigService";

@Injectable({
  providedIn: 'root'
})
export class BloodDonorService {
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(
    private apiService: ApiService,
    private config: ConfigService
    ) { }

  getBloodDonor(personalId : String) : Observable<BloodDonor>{
    return this.apiService.get(`${this.config.blood_donors_url}/${personalId}`);
  }

  getCurrentBloodDonor() : Observable<BloodDonor> {
    return this.apiService.get(`${this.config.blood_donors_url}/current`);
  }

  updateBloodDonor(BloodDonorDto : BloodDonor): Observable<any>{
    return this.apiService.put(`${this.config.blood_donors_url}/update`, BloodDonorDto);
  }

  updateBloodDonorPassword(passwordDto : PasswordDto): Observable<any>{
    return this.apiService.put(`${this.config.users_url}/password/update`, passwordDto);
  }
}
