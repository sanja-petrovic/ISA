import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {config, Observable} from 'rxjs';
import {ApiService} from "./ApiService";
import {ConfigService} from "./ConfigService";
import {BloodBank} from "../model/BloodBank";
import {BloodRequest} from "../model/BloodRequest";

@Injectable({
  providedIn: 'root'
})
export class BloodRequestService {

  headers: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(
    private apiService: ApiService,
    private config: ConfigService) {
  }

  getAll(): Observable<BloodRequest[]> {
    return this.apiService.get(`${this.config.blood_requests_url}/approved`);
  }

  getById(id: string): Observable<BloodRequest> {
    return this.apiService.get(`${this.config.blood_requests_url}/${id}`);
  }
}
