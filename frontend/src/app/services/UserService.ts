import {Injectable} from '@angular/core';
import {map} from 'rxjs/operators';
import {ApiService} from "./ApiService";
import {ConfigService} from "./ConfigService";
import {AuthService} from "./AuthService";
import {User} from "../model/Users";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  currentUser: User | null = null;

  constructor(
    private apiService: ApiService,
    private config: ConfigService
  ) {

  }

  getActiveUser(): Observable<User> | null {
      return this.apiService.get(this.config.current_url);
  }

  setActiveUser() {
    this.getActiveUser().subscribe(data => this.currentUser = data);
  }

  parseJwt(token) {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function (c) {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
  }

  getRoleFromToken(token) {
    return this.parseJwt(token)['role'];
  }

  getAll() {
    return this.apiService.get(this.config.users_url);
  }
  search(searchParam : string[]){
    return this.apiService.get(this.config.current_url + 'search',searchParam );
  }


}
