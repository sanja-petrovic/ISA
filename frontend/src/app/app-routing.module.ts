import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "./home/home.component";
import {BanksPageComponent} from "./banks-page/banks-page.component";
import {RegistrationComponent} from "./registration/registration.component";
import {LoginComponent} from "./login/login.component";
import {VerificationComponent} from "./verification/verification.component";
import {MedicalStaffComponent} from "./medical-staff/medical-staff.component"
import {ProfileComponent} from './profile/profile.component';
import {QuestionnaireComponent} from "./questionnaire/questionnaire.component";
import {BankRegistrationComponent} from './bank-registration/bank-registration.component';
import {MedicalStaffBloodBankComponent} from './medical-staff-blood-bank/medical-staff-blood-bank.component';
import {MedicalStaffRegistrationComponent} from './medical-staff-registration/medical-staff-registration.component';
import {ViewUsersComponent} from './view-users/view-users.component';
import {AppointmentsComponent} from "./appointments/appointments.component";

const routes: Routes = [{
  path: 'home', component: HomeComponent
}, {
  path: '', component: BanksPageComponent
}, {
  path: 'register', component: RegistrationComponent
}, {
  path: 'login', component: LoginComponent
}, {
  path: 'verify/:email', component: VerificationComponent
}, {
  path: 'medical-staff', component: MedicalStaffComponent
}, {
  path: 'profile', component: ProfileComponent
}, {
  path: 'questionnaire', component: QuestionnaireComponent
}, {
  path: 'blood-banks/register', component: BankRegistrationComponent
}, {
  path: 'medical-staff/bank', component: MedicalStaffBloodBankComponent
}, {
  path: 'medical-staff/register', component: MedicalStaffRegistrationComponent
}, {
  path: 'users', component: ViewUsersComponent
}, {
  path: 'blood-bank/:id/appointments', component: AppointmentsComponent
}]

@NgModule({
  imports: [RouterModule.forRoot(routes)], exports: [RouterModule]
})
export class AppRoutingModule {
}
