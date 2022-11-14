import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { BloodBank } from '../model/BloodBank';
import { MedicalStaff } from '../model/Users';
import { AuthService } from '../services/AuthService';
import { BloodBankService } from '../services/BloodBankService';
import { MedicalStaffService } from '../services/MedicalStaffService';
import { UserService } from '../services/UserService';

@Component({
  selector: 'app-medical-staff-blood-bank',
  templateUrl: './medical-staff-blood-bank.component.html',
  styleUrls: ['./medical-staff-blood-bank.component.css']
})
export class MedicalStaffBloodBankComponent implements OnInit {
  
  constructor(private bloodBankService: BloodBankService, private medicalStaffService: MedicalStaffService, private authService : AuthService,
    private router: Router, private snackBar: MatSnackBar, public dialog: MatDialog,
    private userService: UserService ) { 
      this.fillData();
  }

  ngOnInit(): void {
  }
  
  public medicalStaff: MedicalStaff | undefined;
  public bloodBank: BloodBank;
  
  fillData() {
    this.medicalStaff = {...this.userService.currentUser as unknown as MedicalStaff}
    this.bloodBank = {...this.medicalStaff.bloodBank as unknown as BloodBank}
    this.medicalStaffService.getBloodBank().subscribe(
      data => {
      this.bloodBank = data;
    },
    error => {
      if (error.status = 500){
        this.openSnackBar('Can not load blood bank!', 'Close');
      }
    });
  }

  cancelClick(): void {
      this.fillData();
  }

  saveClick(): void {
    if (this.checkInputData()) {
      if (confirm("Confirm changes?")) {   
         this.updateBloodBank();
      }
    }
  }

  updateBloodBank(): void {
    this.bloodBankService.updateBloodBank(this.bloodBank).subscribe(
      data => {
      this.bloodBank = data;
      this.fillData();
      this.openSnackBar('Successfully edited profile', 'Close');
    },
    error => {
      if (error.status = 500){
        this.openSnackBar('Unsuccessfully edited profile!', 'Close');
        this.fillData();
      }
      });
}

  checkInputData() : boolean {
    if (this.isRequiredDataNotEmpty()) {
      return true;
    }
    return false;
  }

 

  isRequiredDataNotEmpty() : boolean {
    if (!this.bloodBank.title || !this.bloodBank.description
          || !this.bloodBank.street || !this.bloodBank.city
          || !this.bloodBank.country || !this.bloodBank.averageGrade) {
      this.openSnackBar('You must fill all of the fields!', 'Close');
      return false;
    }
    return true;
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
