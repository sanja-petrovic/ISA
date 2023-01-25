import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Appointment } from '../model/Appointment';
import { AppointmentService } from '../services/AppointmentService';

@Component({
  selector: 'app-donors-appointments',
  templateUrl: './donors-appointments.component.html',
  styleUrls: [
    './donors-appointments.component.css',
    '../banks-page/banks-page.component.css',
  ],
})
export class DonorsAppointmentsComponent implements OnInit {
  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private appointmentService: AppointmentService,
    private route: ActivatedRoute,
    private datePipe: DatePipe
  ) {}

  public appointments: Appointment[] = [];
  public upcoming: Appointment[] = [];
  public past: Appointment[] = [];
  public dataSourceUpcoming;
  public dataSourcePast;
  displayedColumnsUpcoming: string[] = [
    'qrCode',
    'bloodBank',
    'dateTime',
    'duration',
    'status',
    'cancel',
  ];
  displayedColumns: string[] = [
    'qrCode',
    'bloodBank',
    'dateTime',
    'duration',
    'status',
  ];

  getAppointments() {
    this.getUpcoming();
    this.getPast();
  }

  getUpcoming(sortDirection?: string, sortProperty?: string) {
    if (!!sortDirection || !!sortProperty) {
      this.appointmentService
        .getAllUpcomingByLoggedInBloodDonor(sortDirection, sortProperty)
        .subscribe((data) => {
          this.upcoming = data;
          this.dataSourceUpcoming = new MatTableDataSource(data);
        });
    } else {
      this.appointmentService
        .getAllUpcomingByLoggedInBloodDonor()
        .subscribe((data) => {
          this.upcoming = data;
          this.dataSourceUpcoming = new MatTableDataSource(data);
        });
    }
  }

  getPast(sortDirection?: string, sortProperty?: string) {
    if (!!sortDirection || !!sortProperty) {
      this.appointmentService
        .getAllPastByLoggedInBloodDonor(sortDirection, sortProperty)
        .subscribe((data) => {
          this.past = data;
          this.dataSourcePast = new MatTableDataSource(data);
        });
    } else {
      this.appointmentService
        .getAllPastByLoggedInBloodDonor()
        .subscribe((data) => {
          this.past = data;
          this.dataSourcePast = new MatTableDataSource(data);
        });
    }
  }

  ngOnInit(): void {
    this.getAppointments();
  }

  formatDate(appointment: Appointment): string {
    return this.datePipe.transform(appointment.dateTime, 'dd.MM.yyyy, HH:mm');
  }

  formatStatus(appointment: Appointment): string {
    let status: string = appointment.status.replace('_', ' ');
    return status.charAt(0) + status.slice(1).toLowerCase();
  }

  cancel(appointment: Appointment): void {
    this.appointmentService.cancel(appointment.id).subscribe(
      (res) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Appointment canceled.',
        });
        setTimeout(() => window.location.reload(), 1000);
      },
      (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: error.error.message,
        });
      }
    );
  }

  confirmCancel(appointment: Appointment) {
    this.confirmationService.confirm({
      message: `You're about to cancel your appointment at ${appointment.bloodBankTitle} for ${appointment.dateTime}.`,
      accept: () => this.cancel(appointment),
    });
  }

  handleSortChange(sortState: Sort, table: string) {
    let sortDirection: string = sortState.direction;
    let sortProperty: string = sortState.active;
    console.log(sortState);
    if (table === 'upcoming') {
      this.getUpcoming(sortDirection, sortProperty);
    } else if (table === 'past') {
      this.getPast(sortDirection, sortProperty);
    }
  }

  @ViewChild(MatSort) sort: MatSort;

  ngAfterViewInit() {
    this.dataSourceUpcoming.sort = this.sort;
  }
}
