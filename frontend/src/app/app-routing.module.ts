import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "./home/home.component";
import {BanksPageComponent} from "./banks-page/banks-page.component";
import {RegistrationComponent} from "./registration/registration.component";
import {LoginComponent} from "./login/login.component";
import {VerificationComponent} from "./verification/verification.component";
import {MedicalStaffComponent} from "./medical-staff/medical-staff.component"
import { ProfileComponent } from './profile/profile.component';
import {QuestionnaireComponent} from "./questionnaire/questionnaire.component";
import { BankRegistrationComponent } from './bank-registration/bank-registration.component';
import { MedicalStaffBloodBankComponent } from './medical-staff-blood-bank/medical-staff-blood-bank.component';
import { MedicalStaffRegistrationComponent } from './medical-staff-registration/medical-staff-registration.component';
import { ViewUsersComponent } from './view-users/view-users.component';
import { AuthGuard } from './guards/auth-guard';

const appRoutes: Routes = [
  {
    path: 'home', component: HomeComponent, canActivate: [AuthGuard]
  },
  {
    path: '', component: BanksPageComponent
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
    path: 'medical-staff', component: MedicalStaffComponent, canActivate: [AuthGuard]
  },
  {
    path: '**', redirectTo: '' 
  },
  {
    path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]
  },
  {
    path: 'questionnaire', component: QuestionnaireComponent, canActivate: [AuthGuard]
  },
  {
    path: 'registerBank', component: BankRegistrationComponent, canActivate: [AuthGuard]
  },
  {
    path: 'medical-staff/bank', component: MedicalStaffBloodBankComponent, canActivate: [AuthGuard]
  },
  {
    path: 'medical-staff/register', component: MedicalStaffRegistrationComponent, canActivate: [AuthGuard]
  },
  {
    path:'users', component: ViewUsersComponent, canActivate: [AuthGuard]
  }
]

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

export const routing = RouterModule.forRoot(appRoutes);