import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from "./ApiService";
import { ConfigService } from "./ConfigService";
import { Supplies } from '../model/Supplies';

@Injectable({
  providedIn: 'root'
})
export class SuppliesService {

  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(
    private apiService: ApiService,
    private config: ConfigService) {
  }

  getAll(): Observable<Supplies[]> {
    return this.apiService.get(`${this.config.supplies_url}`);
  }

  update(supplies: Supplies): Observable<void>{
    return this.apiService.put(`${this.config.supplies_url}/update`, supplies);
  }

}
