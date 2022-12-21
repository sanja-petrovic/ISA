import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  private _api_url = 'http://localhost:8080/api';
  private _auth_url = 'http://localhost:8080/api/auth';
  public users_url = this._api_url + '/users';
  private _login_url = this._auth_url + '/log-in';
  public verification_url = this._auth_url + '/verify';
  public questions_url = this._api_url + '/questions'
  public answers_url = this._api_url + '/answers'
  public appointments_url = this._api_url + '/appointments'
  public blood_banks_url = this._api_url + '/blood-banks';
  public medical_staff_url = this._api_url + '/medical-stuff';
  public blood_donors_url = this._api_url + '/blood-donors';
  public log_out_url = this._auth_url + '/log-out';
  public refresh_token_url = this._auth_url + '/refresh-token';
  public admin_url = this._api_url+'/admin';
  public new_scheduling_url = this._api_url + '/appointments/create'
  public available_bank_url = this._api_url + '/appointments/blood-donor/check-available'
  public admin_url = this._api_url+'/admin';


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
