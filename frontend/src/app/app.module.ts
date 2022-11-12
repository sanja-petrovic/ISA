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
import { ProfileComponent } from './profile/profile.component';
import { ChangePasswordDialogComponent } from './change-password-dialog/change-password-dialog.component';
import {MatDialogModule, MatDialog, MatDialogRef,MAT_DIALOG_DATA, MAT_DIALOG_DEFAULT_OPTIONS} from '@angular/material/dialog';
import {VerificationComponent} from './verification/verification.component';
import {QuestionnaireComponent} from './questionnaire/questionnaire.component';
import {MatRadioModule} from "@angular/material/radio";
import {TokenInterceptor} from './interceptor/TokenInterceptor';
import {AuthService} from "./services/AuthService";
import {UserService} from "./services/UserService";
import {ConfigService} from "./services/ConfigService";
import {ApiService} from "./services/ApiService";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    BanksPageComponent,
    RegistrationComponent,
    LoginComponent,
    VerificationComponent,
    ProfileComponent,
    ChangePasswordDialogComponent,
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
		MatDialogModule,
		MatRadioModule
	],
  providers: [
    {
		provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: false}
	}
	 QuestionnaireComponent,
	{
		provide: HTTP_INTERCEPTORS,
		useClass: TokenInterceptor,
		multi: true
    },
    AuthService,
    ApiService,
    UserService,
    ConfigService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
