import { DatePipe } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatChipsModule } from '@angular/material/chips';
import {
  MatDialogModule,
  MAT_DIALOG_DEFAULT_OPTIONS,
} from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { BrowserModule } from '@angular/platform-browser';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DataViewModule } from 'primeng/dataview';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { ToastModule } from 'primeng/toast';
import { AdminProfileComponent } from './admin-profile/admin-profile.component';
import { AdminRegistrationComponent } from './admin-registration/admin-registration/admin-registration.component';
import { AdminSchedulingComponent } from './admin-scheduling/admin-scheduling.component';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AppointmentsComponent } from './appointments/appointments.component';
import { BankRegistrationComponent } from './bank-registration/bank-registration.component';
import { BanksPageComponent } from './banks-page/banks-page.component';
import { BloodRequestsListComponent } from './blood-requests-list/blood-requests-list.component';
import { ChangePasswordDialogComponent } from './change-password-dialog/change-password-dialog.component';
import { DonorSchedulingComponent } from './donor-scheduling/donor-scheduling.component';
import { DonorsAppointmentsComponent } from './donors-appointments/donors-appointments.component';
import { AuthGuard } from './guards/auth-guard';
import { HomeComponent } from './home/home.component';
import { TokenInterceptor } from './interceptor/TokenInterceptor';
import { LocationSimulatorComponent } from './location-simulator/location-simulator.component';
import { LoginComponent } from './login/login.component';
import { MapComponent } from './map/map.component';
import {
  CheckBloodSuppliesDialog,
  MedStaffSchedulePageComponent,
} from './med-staff-schedule-page/med-staff-schedule-page.component';
import { MedicalStaffBloodBankComponent } from './medical-staff-blood-bank/medical-staff-blood-bank.component';
import { MedicalStaffRegistrationComponent } from './medical-staff-registration/medical-staff-registration.component';
import { MedicalStaffScheduleComponent } from './medical-staff-schedule/medical-staff-schedule.component';
import { MedicalStaffComponent } from './medical-staff/medical-staff.component';
import { NavbarComponent } from './navbar/navbar.component';
import { ProfileComponent } from './profile/profile.component';
import { QuestionnaireComponent } from './questionnaire/questionnaire.component';
import { RegistrationComponent } from './registration/registration.component';
import { ApiService } from './services/ApiService';
import { AuthService } from './services/AuthService';
import { ConfigService } from './services/ConfigService';
import { UserService } from './services/UserService';
import { VerificationComponent } from './verification/verification.component';
import { ViewUsersComponent } from './view-users/view-users.component';
import { BankDonorsComponent } from './bank-donors/bank-donors.component';
import {
  MedicalStaffQrCodeUploaderComponent
} from "./medical-staff-qr-code-uploader/medical-staff-qr-code-uploader.component";

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
    MapComponent,
    BloodRequestsListComponent,
    CheckBloodSuppliesDialog,
    BankDonorsComponent,
    MedicalStaffQrCodeUploaderComponent
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
    ConfirmDialogModule,
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
    ToastModule,
    MatListModule,
    MatTabsModule,
    MatTooltipModule,
    MatTableModule,
    MatSortModule,
    LeafletModule,
    DataViewModule,
    ProgressSpinnerModule,
  ],

  providers: [
    [DatePipe],
    {
      provide: MAT_DIALOG_DEFAULT_OPTIONS,
      useValue: { hasBackdrop: false },
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    },
    QuestionnaireComponent,
    AuthService,
    ConfirmationService,
    MessageService,
    ApiService,
    UserService,
    ConfigService,
    AuthGuard,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
