import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import axios from 'axios';
import { BloodBank } from '../model/BloodBank';
import { MedicalStaff } from '../model/Users';
import { AuthService } from '../services/AuthService';
import { BloodBankService } from '../services/BloodBankService';
import { MedicalStaffService } from '../services/MedicalStaffService';
import { UserService } from '../services/UserService';

@Component({
  selector: 'app-medical-staff-registration',
  templateUrl: './medical-staff-registration.component.html',
  styleUrls: ['./medical-staff-registration.component.css']
})
export class MedicalStaffRegistrationComponent implements OnInit {
  bloodBanks : any[] =[];
  medicalStaff : MedicalStaff;

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
    email: ['', Validators.compose([Validators.required, Validators.email],)],
    password: this.password,
    passwordCheck: this.passwordCheck,
    firstName: ['', Validators.compose([Validators.required])],
    lastName: ['', Validators.compose([Validators.required])],
    occupation: ['', Validators.compose([Validators.required])],
    institution: ['', Validators.compose([Validators.required])],
    gender: ['male', Validators.compose([Validators.required])],
    phoneNumber: ['', Validators.compose([Validators.required, Validators.pattern("[0-9]\+")])],
    personalId: ['', Validators.compose([Validators.required, Validators.pattern("[0-9]{13}")] )],
    homeAddress: ['', Validators.compose([Validators.required])],
    city: ['', Validators.compose([Validators.required])],
    country: ['', Validators.compose([Validators.required])],
    bloodBankId: ['']
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
        matchingControl.setErrors({confirmedValidator: true});
      } else {
        matchingControl.setErrors(null);
      }
    };
  }

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private medicalStaffService: MedicalStaffService,
    private router: Router,
    private bloodBankService: BloodBankService) {
  }

  ngOnInit(): void {
    this.bloodBankService.getAll().subscribe(data => {
      this.bloodBanks = data;
    })
  }

  onSubmit(): void {
    this.medicalStaffService.registerMedicalStaff(this.form.value).subscribe(data => {
        console.log(data);
        this.router.navigate(["/"])
      },
      error => {
        console.log(error);
      });
  }

}
