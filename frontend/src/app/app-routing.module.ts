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
import {AuthGuard} from './guards/auth-guard';
import {BankRegistrationComponent} from './bank-registration/bank-registration.component';
import {MedicalStaffBloodBankComponent} from './medical-staff-blood-bank/medical-staff-blood-bank.component';
import {MedicalStaffRegistrationComponent} from './medical-staff-registration/medical-staff-registration.component';
import {ViewUsersComponent} from './view-users/view-users.component';
import { AdminRegistrationComponent } from './admin-registration/admin-registration/admin-registration.component';
import { AdminSchedulingComponent } from './admin-scheduling/admin-scheduling.component';
import {AppointmentsComponent} from "./appointments/appointments.component";
import { DonorSchedulingComponent } from './donor-scheduling/donor-scheduling.component';
import {DonorsAppointmentsComponent} from "./donors-appointments/donors-appointments.component";
import { AdminProfileComponent } from './admin-profile/admin-profile.component';
import { MedicalStaffScheduleComponent } from './medical-staff-schedule/medical-staff-schedule.component';
import { MedStaffSchedulePageComponent } from './med-staff-schedule-page/med-staff-schedule-page.component';
import { MedicalStaffQrCodeUploaderComponent } from './medical-staff-qr-code-uploader/medical-staff-qr-code-uploader.component';

const appRoutes: Routes = [{
  path: 'home', component: HomeComponent, canActivate: [AuthGuard]
}, {
  path: '', component: BanksPageComponent
}, {
  path: 'register', component: RegistrationComponent
}, {
  path: 'login', component: LoginComponent
}, {
  path: 'verify/:email', component: VerificationComponent
}, {
  path: 'medical-staff', component: MedicalStaffComponent, canActivate: [AuthGuard]
}, {
  path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]
}, {
  path: 'profile/edit', component: ProfileComponent, canActivate: [AuthGuard]
}, {
  path: 'questionnaire', component: QuestionnaireComponent, canActivate: [AuthGuard]
}, {
  path: 'blood-banks/register', component: BankRegistrationComponent, canActivate: [AuthGuard]
}, {
  path: 'medical-staff/bank', component: MedicalStaffBloodBankComponent, canActivate: [AuthGuard]
}, {
  path: 'medical-staff/register', component: MedicalStaffRegistrationComponent, canActivate: [AuthGuard]
}, {
  path: 'users', component: ViewUsersComponent, canActivate: [AuthGuard]
}, {
  path: 'admin/register', component: AdminRegistrationComponent, canActivate: [AuthGuard]
}, {
  path: 'admin/scheduling', component: AdminSchedulingComponent, canActivate: [AuthGuard]
}, {
  path: 'blood-banks/:id/appointments', component: AppointmentsComponent, canActivate: [AuthGuard]
}, {
  path: 'blood-banks/:id/appointments', component: AppointmentsComponent, canActivate: [AuthGuard]
}, {
  path: 'appointments', component: DonorsAppointmentsComponent, canActivate: [AuthGuard]
},{
  path: 'donor/scheduling', component: DonorSchedulingComponent
},{
  path: 'medical-staff/schedule', component: MedicalStaffScheduleComponent, canActivate: [AuthGuard]
},{
  path: 'medical-staff/appointment/:id', component: MedStaffSchedulePageComponent, canActivate: [AuthGuard]
},{
  path: 'medical-staff/qrUpload', component: MedicalStaffQrCodeUploaderComponent, canActivate: [AuthGuard]
},{
  path: '**', redirectTo: 'login'
},]

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

export const routing = RouterModule.forRoot(appRoutes);
