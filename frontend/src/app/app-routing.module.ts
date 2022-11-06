import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "./home/home.component";
import {BanksPageComponent} from "./banks-page/banks-page.component";
import {RegistrationComponent} from "./registration/registration.component";

const routes: Routes = [
  {
    path: 'home', component: HomeComponent
  },
  {
    path: 'banks', component: BanksPageComponent
  },
  {
    path: 'register', component: RegistrationComponent
  }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
