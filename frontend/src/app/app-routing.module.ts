import {NgModule} from '@angular/core';
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
import { MedicalStaffScheduleComponent } from './medical-staff-schedule/medical-staff-schedule.component';
import { MedStaffSchedulePageComponent } from './med-staff-schedule-page/med-staff-schedule-page.component';
import { BankDonorsComponent } from './bank-donors/bank-donors.component';
import {RoleGuard} from "./guards/RoleGuard";
import {LocationSimulatorComponent} from "./location-simulator/location-simulator.component";
import {BloodRequestsListComponent} from "./blood-requests-list/blood-requests-list.component";

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
  path: 'questionnaire', component: QuestionnaireComponent, canActivate: [RoleGuard],
  data: { expectedRole: 'ROLE_DONOR' },
}, {
  path: 'blood-banks/register', component: BankRegistrationComponent, canActivate: [RoleGuard],
  data: { expectedRole: 'ROLE_STAFF' },
}, {
  path: 'medical-staff/bank', component: MedicalStaffBloodBankComponent, canActivate: [RoleGuard],
  data: { expectedRole: 'ROLE_STAFF' },
}, {
  path: 'medical-staff/register', component: MedicalStaffRegistrationComponent, canActivate: [RoleGuard],
  data: { expectedRole: 'ROLE_STAFF' },
}, {
  path: 'users', component: ViewUsersComponent, canActivate: [AuthGuard]
}, {
  path: 'admin/register', component: AdminRegistrationComponent, canActivate: [RoleGuard],
  data: { expectedRole: 'ROLE_STAFF' },
}, {
  path: 'admin/scheduling', component: AdminSchedulingComponent, canActivate: [RoleGuard],
  data: { expectedRole: 'ROLE_STAFF' },
}, {
  path: 'blood-banks/:id/appointments', component: AppointmentsComponent, canActivate: [AuthGuard]
}, {
  path: 'blood-banks/:id/appointments', component: AppointmentsComponent, canActivate: [AuthGuard]
}, {
  path: 'appointments', component: DonorsAppointmentsComponent, canActivate: [RoleGuard],
  data: { expectedRole: 'ROLE_DONOR' },
},{
  path: 'donor/scheduling', component: DonorSchedulingComponent, canActivate: [RoleGuard],
  data: { expectedRole: 'ROLE_DONOR' },
},{
  path: 'medical-staff/schedule', component: MedicalStaffScheduleComponent, canActivate: [RoleGuard],
  data: { expectedRole: 'ROLE_STAFF' },
},{
  path: 'medical-staff/appointment/:id', component: MedStaffSchedulePageComponent, canActivate: [RoleGuard],
  data: { expectedRole: 'ROLE_STAFF' },
},{
  path: 'medical-staff/bank-donors', component: BankDonorsComponent, canActivate: [AuthGuard],
  data: { expectedRole: 'ROLE_STAFF' },
},{
  path: 'blood-requests/pending', component: BloodRequestsListComponent, canActivate: [RoleGuard],
  data: { expectedRole: 'ROLE_ADMIN' },
},
  {
  path: 'location-simulator', component: LocationSimulatorComponent, canActivate: [RoleGuard],
    data: { expectedRole: 'ROLE_ADMIN' },
  },{
  path: '**', redirectTo: '/'
},]

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

export const routing = RouterModule.forRoot(appRoutes);
