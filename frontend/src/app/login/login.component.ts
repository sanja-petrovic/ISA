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
              console.log(this.userService.currentUser)
              console.log(user)
              this.userService.currentUser = user;
              if(user.email == 'isidorapoznanovic1@gmail.com'){
                this.logAndmin();
              } else if(user.role == 'ROLE_ADMIN'){
                this.router.navigate(['/admin'])
              } else if(user.role =='ROLE_STAFF'){
                this.router.navigate(['/medical-staff/schedule'])
              }else{
                this.router.navigate(['/banks'])
              }
            })
    },
      (error) => {
      console.log(error);
      })
    // this.authService.login(this.form.value).subscribe({
    //   next: (v) => {
    //     this.userService.getMyInfo().subscribe( data => {
    //    //   console.log(this.userService.currentUser)
    //       console.log(data)
    //       // if(this.userService.currentUser.email == 'isidorapoznanovic1@gmail.com'){
    //       //   this.logAndmin(this.form.value.email);
    //       // }
    //     })
    //   },
    //   error: (e) => console.error(e),
    //   complete: () => {

    //   }
    // })
  }

  public logAndmin(){
    this.router.navigate(['/medical-staff'])
  }
}
