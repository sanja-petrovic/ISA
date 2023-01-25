import { Component, OnInit } from '@angular/core';
import {AppointmentService} from "../services/AppointmentService";
import {Appointment} from "../model/Appointment";
import {ActivatedRoute, Router} from '@angular/router';
import {DatePipe} from '@angular/common';
import { BloodBank } from '../model/BloodBank';
import { BloodBankService } from '../services/BloodBankService';
import {ConfirmationService, MessageService} from "primeng/api";
@Component({
  selector: 'app-appointments',
  templateUrl: './appointments.component.html',
  styleUrls: ['./appointments.component.css', '../banks-page/banks-page.component.css']
})
export class AppointmentsComponent implements OnInit {
  constructor(private messageService: MessageService, private appointmentService: AppointmentService, private confirmationService: ConfirmationService, private bloodBankService: BloodBankService, private route: ActivatedRoute, private router: Router, private datePipe: DatePipe) { }

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

  confirm(appointment: Appointment) {
    this.confirmationService.confirm({
      message: `You're about to schedule an appointment at ${appointment.bloodBankTitle} for ${appointment.dateTime}.`,
      accept: () => this.schedule(appointment)
    })
  }

  schedule(appointment: Appointment): void {
    this.appointmentService.schedule(appointment.id).subscribe(res => {
      this.messageService.add({severity:'success', summary:'Success', detail:'Appointment scheduled.'});
      setTimeout(() => window.location.reload(), 1000);
    },
    error => {
      this.messageService.add({severity:'error', summary:'Error', detail: error.error.message});
    })
  }
}
