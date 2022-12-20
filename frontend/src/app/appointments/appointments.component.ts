import { Component, OnInit } from '@angular/core';
import {AppointmentService} from "../services/AppointmentService";
import {Appointment} from "../model/Appointment";
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-appointments',
  templateUrl: './appointments.component.html',
  styleUrls: ['./appointments.component.css']
})
export class AppointmentsComponent implements OnInit {

  constructor(private appointmentService: AppointmentService, private route: ActivatedRoute) { }

  public appointments: Appointment[] = [];
  public id: string;

  getAppointments() {
    this.appointmentService.getAllByBloodBank(this.id).subscribe(data => {
      this.appointments = data;
    })
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.getAppointments();
  }

}
