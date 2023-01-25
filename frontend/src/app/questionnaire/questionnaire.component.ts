import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Answer } from '../model/Answer';
import { Question } from '../model/Question';
import { BloodDonor } from '../model/Users';
import { AnswerService } from '../services/AnswerService';
import { BloodDonorService } from '../services/BloodDonorService';
import { QuestionService } from '../services/QuestionService';

@Component({
  selector: 'app-questionnaire',
  templateUrl: './questionnaire.component.html',
  styleUrls: ['./questionnaire.component.css', '../app.component.css'],
})
export class QuestionnaireComponent implements OnInit {
  questions: Question[] = [];
  answers: Answer[];
  bloodDonor: BloodDonor;

  constructor(
    private messageService: MessageService,
    private questionService: QuestionService,
    private answerService: AnswerService,
    private bloodDonorService: BloodDonorService,
    private router: Router
  ) {
    this.answers = [];
    bloodDonorService.getCurrentBloodDonor()?.subscribe((data) => {
      this.bloodDonor = data;
      this.questionService.getAll().subscribe((questionData) => {
        if (this.bloodDonor.gender === 'MALE') {
          questionData = questionData.filter(
            (question) => question.type === 'FOR_ALL'
          );
          this.questions = questionData;
        } else {
          this.questions = questionData;
        }
        for (let i = 0; i < questionData.length; i++) {
          this.answers[i] = null;
        }
      });
    });
  }

  ngOnInit(): void {}

  onSubmit(): void {
    this.answerService.save(this.answers);
    this.messageService.add({
      severity: 'success',
      summary: 'Success',
      detail: 'Questionnaire filled.',
    });
    setTimeout(() => this.router.navigate(['/']), 1000);
  }

  handleClick(questionId: string, index: number, value: boolean) {
    let answer: Answer = {
      questionId: questionId,
      answerValue: value,
      user: this.bloodDonor.email,
    };
    this.answers[index] = answer;
  }
}
