import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { of } from 'rxjs/internal/observable/of';
import { Observable } from 'rxjs';
import {ApiService} from "./ApiService";
import {ConfigService} from "./ConfigService";
import {UserService} from "./UserService";
import {Credentials, Patient} from "../model/Users";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private apiService: ApiService,
    private config: ConfigService,
    private userService: UserService,
    private router: Router
  ) {
  }

  private access_token = localStorage.getItem("jwt");

  login(user: Credentials) {
    const loginHeaders = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    });
    const body = {
      'username': user.email,
      'password': user.password
    };
    return this.apiService.post(this.config.login_url, JSON.stringify(body), loginHeaders)
      .pipe(map((res) => {
        console.log(res);
        this.access_token = res.accessToken;
        localStorage.setItem("jwt", res.accessToken)
      }));
  }

  register(user: Patient) {
    const registerHeaders = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    });
    return this.apiService.post(this.config.register_url, JSON.stringify(user), registerHeaders)
      .pipe(map(() => {
        console.log('Register success');
      }));
  }

  verify(email: string) {
    return this.apiService.post(`${this.config.verification_url}/${email}`, email);
  }

  logout() {
    this.userService.currentUser = null;
    this.access_token = null;
    localStorage.clear();
    this.router.navigate(['/login']);
    return this.apiService.post(`${this.config.log_out_url}`, "").subscribe();
  }

  tokenIsPresent() {
    return localStorage.getItem("jwt") !== null;
  }

  refreshToken() {
    return this.apiService.post(`${this.config.refresh_token_url}`, "");
  }

}
