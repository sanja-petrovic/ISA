import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  private _api_url = 'http://localhost:8080';
  private _auth_url = 'http://localhost:8080/auth';
  public users_url = this._api_url + '/users';
  private _login_url = this._auth_url + '/login';

  get login_url(): string {
    return this._login_url;
  }

  private _current_url = this.users_url + '/current';

  get current_url(): string {
    return this._current_url;
  }

  private _register_url = this._auth_url + '/register';

  get register_url(): string {
    return this._register_url;
  }

}
