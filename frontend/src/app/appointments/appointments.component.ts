import { Component, OnInit } from '@angular/core';
import {AppointmentService} from "../services/AppointmentService";
import {Appointment} from "../model/Appointment";
import {ActivatedRoute, Router} from '@angular/router';
import {DatePipe} from '@angular/common';
import { BloodBank } from '../model/BloodBank';
import { BloodBankService } from '../services/BloodBankService';
@Component({
  selector: 'app-appointments',
  templateUrl: './appointments.component.html',
  styleUrls: ['./appointments.component.css', '../banks-page/banks-page.component.css']
})
export class AppointmentsComponent implements OnInit {
  constructor(private appointmentService: AppointmentService, private bloodBankService: BloodBankService, private route: ActivatedRoute, private router: Router, private datePipe: DatePipe) { }

  public appointments: Appointment[] = [];
  public bloodBank: BloodBank;

  public id: string;

  getAppointments() {
    this.appointmentService.getAllAvailableByBloodBank(this.id).subscribe(data => {
      this.appointments = data;
      console.log(data);
    })
  }

  getBloodBank() {
    this.bloodBankService.getAll().subscribe(data => {
      this.bloodBank = data.filter(b => b.id === this.id).at(0);
    })
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.getAppointments();
    this.getBloodBank();
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
      window.location.reload();
    },
    error => {
      alert(error.error.message);
    })
  }
}
