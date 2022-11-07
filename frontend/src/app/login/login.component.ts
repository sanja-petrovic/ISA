import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../services/AuthService";
import {UserService} from "../services/UserService";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private userService: UserService,
    private router: Router) {
  }
  form: FormGroup = this.formBuilder.group({
    email: ['', Validators.compose([Validators.required, Validators.email])],
    password: ['', Validators.compose([Validators.required])]
  })

  ngOnInit(): void {
  }

  onSubmit(): void {
    this.authService.login(this.form.value).subscribe((data) => {
      console.log(data)
    },
      (error) => {
      console.log(error);
      })
  }

}
