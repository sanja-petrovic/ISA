import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../services/AuthService";
import {UserService} from "../services/UserService";
import {Router} from "@angular/router";
import {QuestionService} from "../services/QuestionService";
import {Question} from "../model/Question";
import {Answer} from "../model/Answer";
import {Patient} from "../model/Users";

@Component({
  selector: 'app-questionnaire',
  templateUrl: './questionnaire.component.html',
  styleUrls: ['./questionnaire.component.css']
})
export class QuestionnaireComponent implements OnInit {

  // @ts-ignore
  questions: Question[];
  answers: Answer[];
  user: any;
  constructor(
    private questionService: QuestionService,
    private userService: UserService,
    private router: Router) {
    this.questionService.getAll().subscribe(data => {
      this.questions = data;
      for(let i = 0; i < data.length; i++) {
        // @ts-ignore
        this.answers[i] = null;
      }
    });
    this.answers = [];
    this.user = userService.currentUser;
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
  }

  onSelect() {
    console.log(this.answers);
  }

  handleClick(questionId: string, index: number, value: boolean) {
    let answer: Answer = {
      questionId: questionId,
      answerValue: value,
      user: this.user
    }
    this.answers[index] = answer;
    console.log(this.answers);
  }
}
