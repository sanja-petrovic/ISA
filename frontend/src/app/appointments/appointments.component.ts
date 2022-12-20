import { Component, OnInit } from '@angular/core';
import {AppointmentService} from "../services/AppointmentService";
import {Appointment} from "../model/Appointment";
import { ActivatedRoute } from '@angular/router';
import {DatePipe} from '@angular/common';
@Component({
  selector: 'app-appointments',
  templateUrl: './appointments.component.html',
  styleUrls: ['./appointments.component.css', '../banks-page/banks-page.component.css']
})
export class AppointmentsComponent implements OnInit {
  constructor(private appointmentService: AppointmentService, private route: ActivatedRoute, private datePipe: DatePipe) { }

  public appointments: Appointment[] = [];

  public id: string;

  getAppointments() {
    this.appointmentService.getAllAvailableByBloodBank(this.id).subscribe(data => {
      this.appointments = data;
    })
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.getAppointments();
  }

  formatDate(appointment: Appointment): string {
    return this.datePipe.transform(appointment.dateTime, "dd.MM.yyyy, HH:mm");
  }

  formatStatus(appointment: Appointment): string {
    return appointment.status.replace("_", " ").toLocaleLowerCase();
  }

  schedule(appointment: Appointment): void {
    this.appointmentService.schedule(appointment.id).subscribe(res => {
      console.log(res);
    },
    error => {
      alert(error.error.message);
    })
  }
}
