import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';
import {AppRoutingModule} from './app-routing.module';
import {MatMenuModule} from '@angular/material/menu';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatChipsModule} from '@angular/material/chips';
import {BanksPageComponent} from './banks-page/banks-page.component';
import {MatCardModule} from '@angular/material/card';
import {MatIconModule} from '@angular/material/icon';
import {MatSelectModule} from '@angular/material/select';
import {RegistrationComponent} from './registration/registration.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from "@angular/material/form-field";
import {LoginComponent} from './login/login.component';
import {MatCheckboxModule} from "@angular/material/checkbox";
import { VerificationComponent } from './verification/verification.component';
import { MedicalStaffComponent } from './medical-staff/medical-staff.component';
import { MatDividerModule } from '@angular/material/divider';
import { ProfileComponent } from './profile/profile.component';
import { ChangePasswordDialogComponent } from './change-password-dialog/change-password-dialog.component';
import {MatDialogModule, MatDialog, MatDialogRef,MAT_DIALOG_DATA, MAT_DIALOG_DEFAULT_OPTIONS} from '@angular/material/dialog';
import {QuestionnaireComponent} from './questionnaire/questionnaire.component';
import {MatRadioModule} from "@angular/material/radio";
import {TokenInterceptor} from './interceptor/TokenInterceptor';
import {AuthService} from "./services/AuthService";
import {UserService} from "./services/UserService";
import {ConfigService} from "./services/ConfigService";
import {ApiService} from "./services/ApiService";
import { BankRegistrationComponent } from './bank-registration/bank-registration.component';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MedicalStaffBloodBankComponent } from './medical-staff-blood-bank/medical-staff-blood-bank.component';
import { MedicalStaffRegistrationComponent } from './medical-staff-registration/medical-staff-registration.component';
import {MatListModule} from '@angular/material/list';
import { ViewUsersComponent } from './view-users/view-users.component';
import { NavbarComponent } from './navbar/navbar.component';
import { AuthGuard } from './guards/auth-guard';
import { AppointmentsComponent } from './appointments/appointments.component';
import { DonorsAppointmentsComponent } from './donors-appointments/donors-appointments.component';
import { AdminRegistrationComponent } from './admin-registration/admin-registration/admin-registration.component';
import { AdminSchedulingComponent } from './admin-scheduling/admin-scheduling.component';
import { DonorSchedulingComponent } from './donor-scheduling/donor-scheduling.component';
import { DatePipe } from '@angular/common';
import {MatTabsModule} from "@angular/material/tabs";
import { AdminProfileComponent } from './admin-profile/admin-profile.component';
import { MedicalStaffScheduleComponent } from './medical-staff-schedule/medical-staff-schedule.component';
import { MedStaffSchedulePageComponent, CheckBloodSuppliesDialog } from './med-staff-schedule-page/med-staff-schedule-page.component';
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatTableModule} from "@angular/material/table";
import {MatSortModule} from "@angular/material/sort";
import { LocationSimulatorComponent } from './location-simulator/location-simulator.component';
import { MapComponent } from './map/map.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    BanksPageComponent,
    RegistrationComponent,
    LoginComponent,
    VerificationComponent,
    QuestionnaireComponent,
    BankRegistrationComponent,
    ProfileComponent,
    ChangePasswordDialogComponent,
    QuestionnaireComponent,
    MedicalStaffComponent,
    QuestionnaireComponent,
    MedicalStaffBloodBankComponent,
    MedicalStaffRegistrationComponent,
    ViewUsersComponent,
    MedicalStaffRegistrationComponent,
    NavbarComponent,
    AppointmentsComponent,
    DonorsAppointmentsComponent,
    AdminRegistrationComponent,
    AdminRegistrationComponent,
    AdminSchedulingComponent,
    AdminProfileComponent,
    AdminProfileComponent,
    DonorSchedulingComponent,
    MedicalStaffScheduleComponent,
    MedStaffSchedulePageComponent,
    CheckBloodSuppliesDialog,
    LocationSimulatorComponent,
    MapComponent

  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    BrowserAnimationsModule,
    MatButtonModule,
    AppRoutingModule,
    MatMenuModule,
    MatToolbarModule,
    MatChipsModule,
    MatCardModule,
    MatIconModule,
    MatSelectModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatCheckboxModule,
    ReactiveFormsModule,
    MatDividerModule,
    HttpClientModule,
    BrowserModule,
    BrowserAnimationsModule,
    MatButtonModule,
    AppRoutingModule,
    MatMenuModule,
    MatToolbarModule,
    MatChipsModule,
    MatCardModule,
    MatIconModule,
    MatSelectModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatCheckboxModule,
    ReactiveFormsModule,
    MatRadioModule,
    MatSnackBarModule,
    MatDialogModule,
    FlexLayoutModule,
    MatDialogModule,
    MatRadioModule,
    MatListModule,
    MatTabsModule,
    MatTooltipModule,
    MatTableModule,
    MatSortModule
  ],


  providers: [
    [DatePipe],
    {
		provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: false}
	},
	{
		provide: HTTP_INTERCEPTORS,
		useClass: TokenInterceptor,
		multi: true
	},
	QuestionnaireComponent,
    AuthService,
    ApiService,
    UserService,
    ConfigService,
    AuthGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
