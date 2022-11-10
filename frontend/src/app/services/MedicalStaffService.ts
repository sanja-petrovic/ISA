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

  getMyInfo() {
    return this.apiService.get(this.config.current_url)
      .pipe(map(user => {
        this.currentUser = user;
        return user;
      }));
  }

  getAll() {
    return this.apiService.get(this.config.users_url);
  }

  public getMedicalStaffById(id: string): Observable<MedicalStaff> {
    return this.http
        .get<MedicalStaff>(this.medicalStaffUrl + 'findById/' + id);
  } 

}
