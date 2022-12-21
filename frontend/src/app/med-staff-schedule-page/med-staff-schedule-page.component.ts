import { Component, OnInit, Inject  } from '@angular/core';
import { AppointmentService } from '../services/AppointmentService';
import { BloodDonorService } from '../services/BloodDonorService';
import { MedicalStaffService } from '../services/MedicalStaffService';
import { ActivatedRoute, Router } from '@angular/router';
import { Appointment } from '../model/Appointment';
import { MedicalStaff } from '../model/Users';
import { DatePipe } from '@angular/common';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { EMPTY, map, Observable, startWith } from 'rxjs';
import { BloodSupplyService } from '../services/BloodSupplyService';
import { BloodSupply } from '../model/BloodSupply';
import { Supplies } from '../model/Supplies';
import { SuppliesService } from '../services/SuppliesService';

export interface DialogData {
  quantity: number;
  bloodType: string;
}

@Component({
  selector: 'app-med-staff-schedule-page',
  templateUrl: './med-staff-schedule-page.component.html',
  styleUrls: ['./med-staff-schedule-page.component.css']
})
export class MedStaffSchedulePageComponent implements OnInit {

  constructor(private appointmentService: AppointmentService, private route: ActivatedRoute, private medicalStaffService : MedicalStaffService, private bloodDonorService : BloodDonorService, private router: Router, private datePipe: DatePipe, public dialog: MatDialog) { }

  public appointment: Appointment;
  public staff : MedicalStaff;
  public id : any;
  quantity: number = 0;
  bloodType: string = "";

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.medicalStaffService.getCurrentStaff().subscribe(data => {
      this.staff = data;
      this.appointmentService.getAppointmentsByMedicalStaffForTheTimePeriod("year",this.staff.bloodBankId).subscribe(data => {
        for (let a of data){
          if(a.id === this.id){
            this.appointment = a;
            
            this.bloodDonorService.getBloodDonor(this.appointment.bloodDonorId).subscribe(donor =>{
              this.appointment.bloodDonor = donor;
            })
          }
        }
      });
    });
  }

  formatDate(appointment: Appointment): string {
    return this.datePipe.transform(appointment.dateTime, "dd.MM.yyyy, HH:mm");
  }

  formatStatus(appointment: Appointment): string {
    return appointment.status.replace("_", " ").toLocaleLowerCase();
  }

  startClick(): void {
    this.openDialog()
  }

  notPresentClick(): void {
    if (confirm("Are you sure donor is not present?")) { 
      this.bloodDonorService.increasePenalties(this.appointment.bloodDonor).subscribe((res) => {
        console.log("Penalty given")
        this.cancel(this.appointment);  
        this.router.navigate([`/medical-staff/schedule/`])
      })
    }
  }

  wrongTermsClick(): void {
    if (confirm("Are you sure donor is not satisfying terms of blood donation?")) {   
      this.cancel(this.appointment); 
      this.router.navigate([`/medical-staff/schedule/`])
    }
  }

  openDialog():void{
    const pwDialog = this.dialog.open(CheckBloodSuppliesDialog, {
      width: '250px',
      data: {bloodType: this.bloodType, quantity: this.quantity},
    });
    pwDialog.afterClosed().subscribe(result => {
      this.complete(this.appointment); 
      this.router.navigate([`/medical-staff/schedule/`])
      // this.newPassword = result;
      // this.passwordDto.oldPassword = this.admin.password;
      // this.passwordDto.newPassword = this.newPassword;
      // console.log(this.passwordDto);
      // this.updateAdminPassword();

    });
  }

  cancel(appointment: Appointment): void {
    this.appointmentService.cancel(appointment.id).subscribe(res => {
        console.log(res);
        window.location.reload();
      },
      error => {
        alert(error.error.message);
      })
  }

  complete(appointment: Appointment): void {
    this.appointmentService.complete(appointment.id).subscribe(res => {
        console.log(res);
        window.location.reload();
      },
      error => {
        alert(error.error.message);
      })
  }
}

@Component({
  selector: 'check-blood-supplies-dialog',
  templateUrl: 'check-blood-supplies-dialog.html',
  styleUrls: ['./check-blood-supplies-dialog.css']
})
export class CheckBloodSuppliesDialog {
  myControl = new FormControl('');
  public location: string = '';
  public supplies: Supplies[] = [];
  filteredOptions: Observable<string[]> = EMPTY;
  public visible: boolean = false;
  constructor(
    private bloodsupplyService: BloodSupplyService,
    private suppliesService: SuppliesService,
    public dialogRef: MatDialogRef<CheckBloodSuppliesDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
  ) {}
  ngOnInit(): void {
    this.suppliesService.getAll().subscribe(res =>{
      this.supplies = res;
    })
  }

  onNoClick(): void {
    this.dialogRef.close();
  }


  onCheckClick(quantity: number, bloodType: string): void {
    var bloodSupply: BloodSupply = {id:'',amount:0,bloodType:''};
    console.log(quantity, bloodType)
    this.bloodsupplyService.getAll().subscribe(bloodSupplies => {
      bloodSupply = bloodSupplies.find((s)=> s.bloodType === bloodType);
    //  bloodSupply.type = bloodType;
      bloodSupply.amount += quantity;
      this.bloodsupplyService.update(bloodSupply).subscribe(res => {
      })
    })
    this.dialogRef.close();
  }

  spendSupplies(): void{
    let supl: Supplies = {id:'', amount:0, name:''};
    if(this.visible){
      console.log(this.form.value.suppliesQuantity, this.form.value.suppliesId)
      supl = this.supplies.find((s) => s.id === this.form.value.suppliesId)
      supl.amount -= this.form.value.suppliesQuantity;
      this.suppliesService.update(supl).subscribe(res =>{
        console.log('Updated supplies')
      })
    }
    this.visible = !this.visible
  }

  public form: FormGroup = new FormGroup({
    bloodType: new FormControl(''),
    quantity: new FormControl(''),
    suppliesId: new FormControl(''),
    suppliesQuantity: new FormControl(''),
  });

}