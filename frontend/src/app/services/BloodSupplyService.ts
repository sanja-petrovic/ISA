import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from "./ApiService";
import { ConfigService } from "./ConfigService";
import { BloodSupply } from '../model/BloodSupply';

@Injectable({
  providedIn: 'root'
})
export class BloodSupplyService {

  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(
    private apiService: ApiService,
    private config: ConfigService) {
  }

  getAll(): Observable<BloodSupply[]> {
    return this.apiService.get(`${this.config.blood_supplies_url}`);
  }

  update(supplies: BloodSupply): Observable<void>{
    return this.apiService.put(`${this.config.blood_supplies_url}/update`, supplies);
  }

}
