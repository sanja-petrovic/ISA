import {Injectable} from '@angular/core';
import {map} from 'rxjs/operators';
import {ApiService} from "./ApiService";
import {ConfigService} from "./ConfigService";
import {Question} from "../model/Question";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  constructor(
    private apiService: ApiService,
    private config: ConfigService
  ) {
  }

  getAll(): Observable<Question[]> {
    return this.apiService.get(this.config.questions_url);
  }

}
