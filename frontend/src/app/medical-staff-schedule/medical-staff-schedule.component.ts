import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Appointment } from '../model/Appointment';
import { MedicalStaff } from '../model/Users';
import { AppointmentService } from '../services/AppointmentService';
import { BloodDonorService } from '../services/BloodDonorService';
import { MedicalStaffService } from '../services/MedicalStaffService';
import { Router } from '@angular/router';

@Component({
  selector: 'app-medical-staff-schedule',
  templateUrl: './medical-staff-schedule.component.html',
  styleUrls: ['./medical-staff-schedule.component.css']
})
export class MedicalStaffScheduleComponent implements OnInit {

  constructor(private appointmentService: AppointmentService, private route: ActivatedRoute, private datePipe: DatePipe, private medicalStaffService : MedicalStaffService, private bloodDonorService : BloodDonorService, private router: Router) { }

  public appointments: Appointment[] = [];
  public staff : MedicalStaff;
 

  ngOnInit(): void {
    this.medicalStaffService.getCurrentStaff().subscribe(data => {
      this.staff = data;
      this.appointmentService.getAppointmentsByMedicalStaffForTheTimePeriod("week",this.staff.bloodBankId).subscribe(data => {
        this.appointments = data.filter((appoimtment)=> this.formatStatus(appoimtment)==='scheduled');
      });
    });
    
  }

  formatDate(appointment: Appointment): string {
    return this.datePipe.transform(appointment.dateTime, "dd.MM.yyyy, HH:mm");
  }

  formatStatus(appointment: Appointment): string {
    return appointment.status.replace("_", " ").toLocaleLowerCase();
  }

  getAppointments(period: string): void {
    this.appointmentService.getAppointmentsByMedicalStaffForTheTimePeriod( period ,this.staff.bloodBankId).subscribe(data => {
      this.appointments = data.filter((appoimtment)=> this.formatStatus(appoimtment)==='scheduled');
      for(let i = 0;i<this.appointments.length;i+=1){
        this.bloodDonorService.getBloodDonor(this.appointments[i].bloodDonorId).subscribe(donor =>{
          this.appointments[i].bloodDonor = donor;
        })
      }
      
    }); 
  }

  openAppointmentPage(appointment: Appointment): void{
    this.router.navigate([`/medical-staff/appointment/${appointment.id}`]);
  }

}
