import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
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
import { RegistrationComponent } from './registration/registration.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from "@angular/material/form-field";
import { LoginComponent } from './login/login.component';
import {MatCheckboxModule} from "@angular/material/checkbox";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    BanksPageComponent,
    RegistrationComponent,
    LoginComponent
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
        ReactiveFormsModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
