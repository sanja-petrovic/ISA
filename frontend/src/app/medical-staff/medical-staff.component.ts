import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { MedicalStaff } from '../model/Users';
import { AuthService } from '../services/AuthService';
import { MedicalStaffService } from '../services/MedicalStaffService';
import { UserService } from '../services/UserService';
// import { PharmacyModalDialogComponent } from './pharmacy-modal-dialog/pharmacy-modal-dialog.component';

@Component({
  selector: 'app-medical-staff',
  templateUrl: './medical-staff.component.html',
  styleUrls: ['./medical-staff.component.css']
})
export class MedicalStaffComponent implements OnInit {

  ngOnInit(): void {
      
  }
  public personalId : string | undefined;
  public medicalStaff: MedicalStaff | undefined;
  public firstName: string = '';
  public lastName: string = '';
  public email: string = '';
  public phoneNumber: string = '';
  public password: string = '';
  public repeatPassword: string = '';
  public city: string = '';
  public gender: string = '';
  public country: string = '';

  constructor(private medicalStaffService: MedicalStaffService, private authService : AuthService,
    private router: Router, private snackBar: MatSnackBar, public dialog: MatDialog,
    private userService: UserService ) { 
      this.fillData();
  }

  
  fillData() {
    this.medicalStaffService.getMedicalStaffById('').subscribe(
      data => {
        this.medicalStaff = data;
        this.prepareDate(this.medicalStaff); 
      }
    );
  }

  prepareDate(medicalStaff: MedicalStaff) : void {
    this.personalId = medicalStaff.personalId;
    this.firstName = medicalStaff.firstName;
    this.lastName = medicalStaff.lastName;
    this.email = medicalStaff.email;
    this.phoneNumber = medicalStaff.phoneNumber;
    this.city = medicalStaff.city;
    this.gender = medicalStaff.gender;
    this.country = medicalStaff.country;
  }

  cancelClick(): void {
      this.fillData();
  }

  saveClick(): void {
    if (this.checkInputData()) {
      if (confirm("Da li ste sigurni da želite da sačuvate izmene?")) {   
          // this.updateMedicalStaff();
      }
    }
  }

  // updateMedicalStaff(): void {
  //   this.medicalStaffService.updateMedicalStaff(this.id, new MedicalStaff(this.medicalStaff.id,  this.name, this.surname, this.city, this.country,  this.street, this.email, this.phoneNumber, encodeURIComponent(this.password), 0)).subscribe(
  //     data => {
  //       this.medicalStaff = data;
  //       this.prepareDate(this.medicalStaff);
  //       this.openSnackBar('Uspešno ste izmenili profil!', 'Zatvori');
  //       if (this.password.length > 0) {
  //         this.authService.logout();
  //         this.router.navigate(['login']);
  //       }
  //     },
  //     error => {
  //       if (error.status = 500){
  //         this.openSnackBar('Neuspešna izmena profila!', 'Zatvori');
  //         this.fillData();
  //       }
  //     });
  // }

  checkInputData() : boolean {
    if (this.isPasswordValid() && this.isRequiredDataNotEmpty()) {
      return true;
    }
    return false;
  }

  isPasswordValid() : boolean {
    if (this.password === this.repeatPassword) {
      return true;
    }
    this.openSnackBar('Nova loznika i lozinka za potvrdu moraju biti iste!', 'Zatvori');
    return false;
  }

  isRequiredDataNotEmpty() : boolean {
    if (this.firstName === '' || this.lastName === ''
          || this.phoneNumber === '' || this.city === ''
            || this.gender === '' || this.country === '') {
      this.openSnackBar('Sva obavezna polja moraju biti popunjena!', 'Zatvori');
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

  openDialog(): void {
    // this.dialog.open(PharmacyModalDialogComponent, {
    //   panelClass: 'my-centered-dialog',
    //   width: '420px',
    //   height: '200px',
    //   position: {left: '675px'}
    // });
  }
}
