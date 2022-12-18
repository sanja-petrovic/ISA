import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { BloodDonorService } from '../services/BloodDonor.service';
import { PasswordDto, BloodDonor } from '../model/Users';
import {MatDialog,MatDialogModule, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { ChangePasswordDialogComponent } from '../change-password-dialog/change-password-dialog.component';
import { Router } from '@angular/router';
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css', '../app.component.css']
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
  public bloodDonor : BloodDonor;
  public editEnabled : boolean = false;
  private newPassword : string ='';
  private passwordDto : PasswordDto = {
    personalId: "",
    oldPassword: "",
    newPassword: ""
  };
  constructor(
    private formBuilder: FormBuilder,
    private bloodDonorService : BloodDonorService,
    public dialog: MatDialog,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.bloodDonorService.getCurrentBloodDonor().subscribe((res: any)=>{
      this.bloodDonor = res;
      this.profileForm.setValue(this.bloodDonor);
    })
    //this.disableSensitive();
    //this.disableSafe();
  }
  saveEditChanges() :void{
    this.bloodDonorService.updateBloodDonor(this.profileForm.getRawValue()).subscribe((res: any)=>{
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
      data: { oldPass: this.bloodDonor.password ,oldPassCheck: '', newPass: '', newPassCheck:''},
    });
    pwDialog.afterClosed().subscribe(result => {
      this.newPassword = result;

      this.passwordDto.personalId = this.bloodDonor.personalId;
      this.passwordDto.oldPassword = this.bloodDonor.password;
      this.passwordDto.newPassword = this.newPassword;
      console.log(this.passwordDto);
      this.updateBloodDonorPassword();

    });
  }
  updateBloodDonorPassword():void{
    this.bloodDonorService.updateBloodDonorPassword(this.passwordDto).subscribe((res: any)=>{
      console.log(res);
      alert("Password change successful, you will be redirected.")
      setTimeout(() =>
      {
          this.router.navigate(['/profile']);
      },
      2000);
    })
  }
}
