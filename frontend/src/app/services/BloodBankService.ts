import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {config, Observable} from 'rxjs';
import {ApiService} from "./ApiService";
import {ConfigService} from "./ConfigService";
import {BloodBank} from "../model/BloodBank";

@Injectable({
  providedIn: 'root'
})
export class BloodBankService {

  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(
    private apiService: ApiService,
    private config: ConfigService) {
  }

  getAll(): Observable<BloodBank[]> {
    return this.apiService.get(`${this.config.blood_banks_url}`);
  }
  createBloodBank(bloodBank: any): Observable<any> {
    return this.apiService.post(`${this.config.blood_banks_url}/register`, bloodBank);
  }

  searchSort(dto: any): any {
    return this.apiService.post(`${this.config.blood_banks_url}/search`, dto);
  }

  updateBloodBank (bloodBank: BloodBank){
    return this.apiService.post(`${this.config.blood_banks_url}/update`, bloodBank);
  }
}
