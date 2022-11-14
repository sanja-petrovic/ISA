import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BloodBank } from '../model/BloodBank';

@Injectable({
  providedIn: 'root'
})
export class BloodBankService {

  apiHost: string = 'http://localhost:8080/';
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) { }

  updateBloodBank (bloodBank: BloodBank){
    return this.http.post<BloodBank>(this.apiHost + 'bloodbanks/update', bloodBank, {headers: this.headers});
  }
}
