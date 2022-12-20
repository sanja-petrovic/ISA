import { Component, OnInit } from '@angular/core';
import {DatePipe} from '@angular/common';
import {AppointmentService} from "../services/AppointmentService";
import {ActivatedRoute} from "@angular/router";
import {Appointment} from "../model/Appointment";

@Component({
  selector: 'app-donors-appointments',
  templateUrl: './donors-appointments.component.html',
  styleUrls: ['./donors-appointments.component.css', '../banks-page/banks-page.component.css', '../appointments/appointments.component.css']
})
export class DonorsAppointmentsComponent implements OnInit {

  constructor(private appointmentService: AppointmentService, private route: ActivatedRoute, private datePipe: DatePipe) { }

  public appointments: Appointment[] = [];
  public upcoming: Appointment[] = [];
  public past: Appointment[] = [];
  public cancelled: Appointment[] = [];
  getAppointments() {
    this.appointmentService.getAllUpcomingByLoggedInBloodDonor().subscribe(data => {
      this.upcoming = data;
    })
    this.appointmentService.getAllPastByLoggedInBloodDonor().subscribe(data => {
      this.past = data;
    })
    this.appointmentService.getAllCancelledByLoggedInBloodDonor().subscribe(data => {
      this.cancelled = data;
    })
  }

  ngOnInit(): void {
    this.getAppointments();
  }

  formatDate(appointment: Appointment): string {
    return this.datePipe.transform(appointment.dateTime, "dd.MM.yyyy, HH:mm");
  }

  formatStatus(appointment: Appointment): string {
    return appointment.status.replace("_", " ").toLocaleLowerCase();
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

}
