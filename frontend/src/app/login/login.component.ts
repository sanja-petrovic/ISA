import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../services/AuthService";
import {UserService} from "../services/UserService";
import {ActivatedRoute, Router} from "@angular/router";

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
    private route: ActivatedRoute,
    private router: Router,
    private actRoute: ActivatedRoute) {
  }
  form: FormGroup = this.formBuilder.group({
    email: ['', Validators.compose([Validators.required, Validators.email])],
    password: ['', Validators.compose([Validators.required])]
  })

  ngOnInit(): void {
  }

  onSubmit(): void {
    this.authService.login(this.form.value).subscribe((data) => {
      this.userService.getActiveUser().subscribe( user => {
              this.userService.currentUser = user;
              if(user.role.name == 'ROLE_ADMIN'){
                this.router.navigate(['/admin'])
              } else if(user.role.name =='ROLE_STAFF'){
                this.router.navigate(['/medical-staff/schedule'])
              }
            })
    },
      (error) => {
      console.log(error);
      })
  }
}
