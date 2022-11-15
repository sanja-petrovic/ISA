import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { PatientService } from '../services/PatientService';
import { PasswordDto, Patient } from '../model/Users';
import {MatDialog,MatDialogModule, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { ChangePasswordDialogComponent } from '../change-password-dialog/change-password-dialog.component';
import { Router } from '@angular/router';
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  profileForm: FormGroup = this.formBuilder.group({
    email: [],
    password: [],
    firstName: [],
    lastName: [],
    occupation: [],
    institution: [],
    gender: [],
    phoneNumber: [],
    personalId: [],
    homeAddress: [],
    city: [],
    country: [],
  });
  private patient : Patient;
  public editEnabled : boolean = false;
  private newPass : string ='';
  private passDto : PasswordDto = {
    personalId: "",
    oldPassword: "",
    newPassword: ""
  };
  constructor(
    private formBuilder: FormBuilder,
    private patientService : PatientService,
    public dialog: MatDialog,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.patientService.getPatient('supersus1').subscribe((res: any)=>{
      this.patient = res;
      this.profileForm.setValue(this.patient);
    })
    this.disableSensitive();
    this.disableSafe();
  }
  saveEditChanges() :void{
    this.patientService.updatePatient(this.profileForm.getRawValue()).subscribe((res: any)=>{
      console.log(res);
    })
  }
  editClicked(): void{
    this.editEnabled = !this.editEnabled;
    if(this.editEnabled){
      this.enableSafe();
    }
    else{
      this.disableSafe();
    }
  }
  disableSensitive():void{
    this.profileForm.get('personalId').disable();
    this.profileForm.get('password').disable();
    this.profileForm.get('email').disable();
  }
  enableSafe():void{
    this.profileForm.get('firstName').enable();
    this.profileForm.get('lastName').enable();
    this.profileForm.get('occupation').enable();
    this.profileForm.get('institution').enable();
    this.profileForm.get('gender').enable();
    this.profileForm.get('phoneNumber').enable();
    this.profileForm.get('homeAddress').enable();
    this.profileForm.get('city').enable();
    this.profileForm.get('country').enable();
  }
  disableSafe():void{
    this.profileForm.get('firstName').disable();
    this.profileForm.get('lastName').disable();
    this.profileForm.get('occupation').disable();
    this.profileForm.get('institution').disable();
    this.profileForm.get('gender').disable();
    this.profileForm.get('phoneNumber').disable();
    this.profileForm.get('homeAddress').disable();
    this.profileForm.get('city').disable();
    this.profileForm.get('country').disable();
  }
  openPasswordDialog():void{
    const pwDialog = this.dialog.open(ChangePasswordDialogComponent, {
      width: '250px',
      data: { oldPass: this.patient.password ,oldPassCheck: '', newPass: '', newPassCheck:''},
    });
    pwDialog.afterClosed().subscribe(result => {
      this.newPass = result;
      
      this.passDto.personalId = this.patient.personalId;
      this.passDto.oldPassword = this.patient.password;
      this.passDto.newPassword = this.newPass;
      console.log(this.passDto);
      this.updatePatientPassword();
      
    });
  }
  updatePatientPassword():void{
    this.patientService.updatePatientPassword(this.passDto).subscribe((res: any)=>{
      console.log(res);
      alert("Password change successfull, you will be redirected.")
      setTimeout(() => 
      {
          this.router.navigate(['/profile']);
      },
      2000);
    })
  }
}
