import {Component, OnInit} from '@angular/core';
import {UserService} from "../services/UserService";
import {Router} from "@angular/router";
import {QuestionService} from "../services/QuestionService";
import {Question} from "../model/Question";
import {Answer} from "../model/Answer";
import {AnswerService} from "../services/AnswerService";

@Component({
  selector: 'app-questionnaire',
  templateUrl: './questionnaire.component.html',
  styleUrls: ['./questionnaire.component.css', '../app.component.css']
})
export class QuestionnaireComponent implements OnInit {

  // @ts-ignore
  questions: Question[];
  answers: any[];
  user: any;

  constructor(
    private questionService: QuestionService,
    private answerService: AnswerService,
    private userService: UserService,
    private router: Router) {
    this.questionService.getAll().subscribe(data => {
      this.questions = data;
      for (let i = 0; i < data.length; i++) {
        // @ts-ignore
        this.answers[i] = null;
      }
    });
    this.answers = [];
    userService.getActiveUser()?.subscribe(data => this.user = data.email);
    console.log(this.user);
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    console.log(this.answers);
    this.answerService.save(this.answers);
  }

  handleClick(questionId: string, index: number, value: boolean) {
    let answer: Answer = {
      questionId: questionId,
      answerValue: value,
      user: this.user
    }
    this.answers[index] = answer;
    if(index == 37) {
      console.log(this.answers);
    }
  }
}
