import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { MedicalStaff } from '../model/Users';
import { AuthService } from '../services/AuthService';
import { MedicalStaffService } from '../services/MedicalStaffService';
import { UserService } from '../services/UserService';

@Component({
  selector: 'app-medical-staff',
  templateUrl: './medical-staff.component.html',
  styleUrls: ['./medical-staff.component.css']
})
export class MedicalStaffComponent implements OnInit {

  ngOnInit(): void {
      
  }
  
  public medicalStaff: MedicalStaff | undefined;
  public repeatPassword: string = '';
  public password: string = '';
 
  constructor(private medicalStaffService: MedicalStaffService, private authService : AuthService,
    private router: Router, private snackBar: MatSnackBar, public dialog: MatDialog,
    private userService: UserService ) { 
      this.fillData();
  }

  
  fillData() {
    this.medicalStaffService.getCurrentStaff().subscribe((res: any)=>{
      this.medicalStaff = res;
      //this.profileForm.setValue(this.bloodDonor);
      console.log(this.medicalStaff)
    })
    // this.medicalStaff = {...this.userService.currentUser as unknown as MedicalStaff}
    
  }

  cancelClick(): void {
      this.fillData();
  }

  saveClick(): void {
    if (this.checkInputData()) {
      if (confirm("Confirm changes?")) {   
         this.updateMedicalStaff();
      }
    }
  }

  updateMedicalStaff(): void {
    if(this.password !== ''){
      this.medicalStaff.password = this.password
    }
    this.medicalStaffService.updateMedicalStaff(this.medicalStaff).subscribe(
      data => {
      this.medicalStaff = data;
      this.fillData();
      this.openSnackBar('Successfully edited profile', 'Close');
      // if (this.password.length > 0) {
      //   this.authService.logout();
      //   this.router.navigate(['login']);
      // }
    },
    error => {
      if (error.status = 500){
        this.openSnackBar('Unsuccessfully edited profile!', 'Close');
        this.fillData();
      }
      });
}

  checkInputData() : boolean {
    if (this.isPasswordValid() && this.isRequiredDataNotEmpty()) {
      return true;
    }
    return false;
  }

  isPasswordValid() : boolean {
    if (this.password === this.repeatPassword) {
      return true;
    }else if((/(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&^_-]).{8,}/).test(this.password)){
      this.openSnackBar('Password must contain upper and lower letter, number, and caracter(@$!%*#?&^_-), no whitespaces!', 'Close');
      return false;
    }
    this.openSnackBar('Please check if repeated password matches with password!', 'Close');
    return false;
  }

  isRequiredDataNotEmpty() : boolean {
    if (this.medicalStaff.firstName === '' || this.medicalStaff.lastName === ''
          || this.medicalStaff.phoneNumber === '' || this.medicalStaff.gender === ''
          || this.medicalStaff.email === '') {
      this.openSnackBar('You must fill all of the fields!', 'Close');
      return false;
    }
    return true;
  }

  openBloodBank() {
    this.router.navigate(['/medical-staff/bank'])
  }

  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000,
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }
}
