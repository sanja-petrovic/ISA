import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ChangePasswordDialogComponent } from '../change-password-dialog/change-password-dialog.component';
import { Admin, PasswordDto } from '../model/Users';
import { AdminService } from '../services/admin.service';

@Component({
  selector: 'app-admin-profile',
  templateUrl: './admin-profile.component.html',
  styleUrls: ['./admin-profile.component.css']
})
export class AdminProfileComponent implements OnInit {

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
  public admin : Admin;
  public editEnabled : boolean = false;
  private newPassword : string ='';
  private passwordDto : PasswordDto = {
    oldPassword: "",
    newPassword: ""
  };
  constructor(
    private formBuilder: FormBuilder,
    private adminService : AdminService,
    public dialog: MatDialog,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.adminService.getCurrentAdmin().subscribe((res: any)=>{
      this.admin = res;
      this.profileForm.setValue(this.admin);
    })
  }
  openPasswordDialog():void{
    const pwDialog = this.dialog.open(ChangePasswordDialogComponent, {
      width: '250px',
      data: { oldPass: this.admin.password ,oldPassCheck: '', newPass: '', newPassCheck:''},
    });
    pwDialog.afterClosed().subscribe(result => {
      this.newPassword = result;

      
      this.passwordDto.oldPassword = this.admin.password;
      this.passwordDto.newPassword = this.newPassword;
      console.log(this.passwordDto);
      this.updateAdminPassword();

    });
  }
  updateAdminPassword():void{
    this.adminService.updateAdminPassword(this.passwordDto).subscribe((res: any)=>{
      console.log(res);
      alert("Password change successful, you will be redirected.")
      setTimeout(() =>
      {
          this.router.navigate(['/profile']);
      },
      2000);
    })
  }
  registerMedicalStaff():void{
    this.router.navigate(['/medical-staff/register'])
  }
  registerAdmin():void{
    this.router.navigate(['/admin/register']);
  }
}
