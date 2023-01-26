import {Component, OnInit} from '@angular/core';
import {BloodBankService} from "../services/BloodBankService";
import axios from 'axios';
import { BloodBank } from '../model/BloodBank';
import { Router } from '@angular/router';
import { AppointmentService } from '../services/AppointmentService';
import { MedicalStaffService } from '../services/MedicalStaffService';
import { Appointment } from '../model/Appointment';
import { BloodDonor, MedicalStaff } from '../model/Users';
import { BloodDonorService } from '../services/BloodDonorService';

@Component({
  selector: 'app-bank-donors',
  templateUrl: './bank-donors.component.html',
  styleUrls: ['./bank-donors.component.css']
})
export class BankDonorsComponent implements OnInit {

  public banks: BloodBank[] = [];
  public searchCriteria: string[] = ["", ""];
  public gradeFilter: string = "0";
  public sortFilter: string = "title-asc";
  public appointments: Appointment[] = [];
  public staff : MedicalStaff;
  public donors: BloodDonor[] = [];
  
  // getBanks(): void {
  //   this.bloodBankService.getAll().subscribe(data => {
  //     this.banks = data;
  //   })
  // }

  getDonors(): void {
    this.medicalStaffService.getCurrentStaff().subscribe(data => {
      this.staff = data;
      this.appointmentService.getAppointmentsByMedicalStaffForTheTimePeriod("week",this.staff.bloodBankId).subscribe(data => {
        this.appointments = data.filter((appoimtment)=> this.formatStatus(appoimtment)==='completed');
        for(let i = 0; i < this.appointments.length; i += 1){
          this.bloodDonorService.getBloodDonor(this.appointments[i].bloodDonorId).subscribe(donor =>{
            this.appointments[i].bloodDonor = donor;
          })
        } 
        console.log(this.appointments)
        this.appointments = data
        // this.appointments.forEach(x -> 
        // this.bloodDonorService.getBloodDonor(this.x.bloodDonorId).subscribe(donor =>{
        //   this.appointment.bloodDonor = donor;
        // })
      });
    });
  }

  formatStatus(appointment: Appointment): string {
    return appointment.status.replace("_", " ").toLocaleLowerCase();
  }

  sortDonors(): void {
    const sort: string[] = this.sortFilter.split('-');
    console.log(sort)
    let direction: string = sort[1]
    let property: string[] = sort[0].split('.')
    console.log(property)
    if (direction === 'asc'){
      if(property.length === 1)
        this.appointments.sort((a,b) => a[property[0]].localeCompare(b[property[0]]));
      else
        this.appointments.sort((a,b) => {
          const aa = a[property[0]][property[1]];
          const bb = b[property[0]][property[1]];
          return aa.localeCompare(bb);
        });
        console.log(this.appointments)
    }else{
      if(property.length === 1)
        this.appointments.sort((a,b) => b[property[0]].localeCompare(a[property[0]]));
      else
        this.appointments.sort((a,b) => {
          const aa = a[property[0]][property[1]];
          const bb = b[property[0]][property[1]];
          return bb.localeCompare(aa);
        });
      
        console.log(this.appointments)
    }
  }

  constructor(private bloodDonorService: BloodDonorService, private medicalStaffService: MedicalStaffService, private appointmentService: AppointmentService, private bloodBankService: BloodBankService, private router: Router) {
  }

  ngOnInit(): void {
    // this.getBanks();
    this.getDonors();
  }

  seeAppointments(id: string): void {
    this.router.navigate([`/blood-banks/${id}/appointments`]);
  }

}
