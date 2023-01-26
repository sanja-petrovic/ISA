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

  getAll() {
    return this.apiService.get(this.config.users_url);
  }
  search(searchParam : string[]){
    return this.apiService.get(this.config.current_url + 'search',searchParam );
  }


}
