import {Injectable} from '@angular/core';
import {map} from 'rxjs/operators';
import {ApiService} from "./ApiService";
import {ConfigService} from "./ConfigService";
import {Question} from "../model/Question";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AnswerService {

  constructor(
    private apiService: ApiService,
    private config: ConfigService
  ) {
  }

  save(answers: Answer[]) {
    this.apiService.post(this.config.answers_url, answers);
  }

}
