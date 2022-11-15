import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PasswordDto, Patient } from '../model/Users';

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  apiHost: string = 'http://localhost:8080/';
  headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  
  constructor(    
    private http: HttpClient,
    ) { }

  getPatient(PersonalId : String) : Observable<Patient>{
    return this.http.get<Patient>(this.apiHost +'patients/get/'+ PersonalId,{headers :this.headers})
  }
  updatePatient(patientDto : Patient): Observable<any>{
    return this.http.put<any>(this.apiHost + 'patients/update', patientDto, {headers: this.headers});
  }
 
  updatePatientPassword(passwordDto : PasswordDto): Observable<any>{
    return this.http.post<any>(this.apiHost + 'users/update/password', passwordDto, {headers: this.headers});
  }
}
