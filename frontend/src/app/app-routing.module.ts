import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "./home/home.component";
import {BanksPageComponent} from "./banks-page/banks-page.component";
import {RegistrationComponent} from "./registration/registration.component";
import {LoginComponent} from "./login/login.component";
import {VerificationComponent} from "./verification/verification.component";
import {QuestionnaireComponent} from "./questionnaire/questionnaire.component";

const routes: Routes = [
  {
    path: 'home', component: HomeComponent
  },
  {
    path: 'banks', component: BanksPageComponent
  },
  {
    path: 'register', component: RegistrationComponent
  },
  {
    path: 'login', component: LoginComponent
  },
  {
    path: 'verify/:email', component: VerificationComponent
  },
  {
    path: 'questionnaire', component: QuestionnaireComponent
  }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
