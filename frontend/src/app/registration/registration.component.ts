import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  passwordsMatching: boolean = false;
  passwordValidator = this.ConfirmedValidator('password', 'passwordCheck');
  confirmPasswordClass = 'form-control';
  password = new FormControl(null, [
    (c: AbstractControl) => Validators.required(c),
    Validators.pattern(
      /(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&^_-]).{8,}/
    ),
  ]);
  passwordCheck = new FormControl(null, [
    (c: AbstractControl) => Validators.required(c)
  ]);
  form: FormGroup = this.formBuilder.group({
    email: ['', Validators.compose([Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")],)],
    password: this.password,
    passwordCheck: this.passwordCheck,
    firstName: ['', Validators.compose([Validators.required])],
    lastName: ['', Validators.compose([Validators.required])],
    occupation: ['', Validators.compose([Validators.required])],
    institution: ['', Validators.compose([Validators.required])],
    gender: ['', Validators.compose([Validators.required])],
    phone: ['', Validators.compose([Validators.required])],
    personalId: ['', Validators.compose([Validators.required])],
    homeAddress: ['', Validators.compose([Validators.required])],
    city: ['', Validators.compose([Validators.required])],
    country: ['', Validators.compose([Validators.required])],
  }, {
    validators: this.ConfirmedValidator('password', 'passwordCheck')
  });

  ConfirmedValidator(controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
      const control = formGroup.controls[controlName];
      const matchingControl = formGroup.controls[matchingControlName];
      if (
        matchingControl.errors &&
        !matchingControl.errors['confirmedValidator']
      ) {
        return;
      }
      if (control.value !== matchingControl.value) {
        matchingControl.setErrors({ confirmedValidator: true });
      } else {
        matchingControl.setErrors(null);
      }
    };
  }
  constructor(
    private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {

  }

}
