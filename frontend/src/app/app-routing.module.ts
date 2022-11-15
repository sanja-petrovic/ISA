import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "./home/home.component";
import {BanksPageComponent} from "./banks-page/banks-page.component";
import {RegistrationComponent} from "./registration/registration.component";
import {LoginComponent} from "./login/login.component";
import {VerificationComponent} from "./verification/verification.component";
import {MedicalStaffComponent} from "./medical-staff/medical-staff.component"
import { ProfileComponent } from './profile/profile.component';
import {QuestionnaireComponent} from "./questionnaire/questionnaire.component";
<<<<<<< HEAD
import { BankRegistrationComponent } from './bank-registration/bank-registration.component';
=======
import { MedicalStaffBloodBankComponent } from './medical-staff-blood-bank/medical-staff-blood-bank.component';

>>>>>>> development
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
    path: 'medical-staff', component: MedicalStaffComponent
  },
  {
    path: 'profile', component: ProfileComponent
  },
  {
    path: 'questionnaire', component: QuestionnaireComponent
  },
  {
<<<<<<< HEAD
    path: 'registerBank', component: BankRegistrationComponent
=======
    path: 'medical-staff/bank', component: MedicalStaffBloodBankComponent
>>>>>>> development
  }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
