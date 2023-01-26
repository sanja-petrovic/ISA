import { Component, OnInit } from '@angular/core';
import {BloodRequest} from "../model/BloodRequest";
import {BloodRequestService} from "../services/BloodRequestService";
import {MatTableDataSource} from "@angular/material/table";
import {Appointment} from "../model/Appointment";
import {DatePipe} from "@angular/common";
import {Router} from "@angular/router";
import {DataService} from "../services/DataService";

@Component({
  selector: 'app-blood-requests-list',
  templateUrl: './blood-requests-list.component.html',
  styleUrls: ['./blood-requests-list.component.css','../banks-page/banks-page.component.css']
})
export class BloodRequestsListComponent implements OnInit {

  public bloodRequests: BloodRequest[];
  public dataSource;
  public displayedColumns: string[] = [
    'bloodBank',
    'bloodType',
    'amount',
    'start'
  ]

  constructor(private bloodRequestService: BloodRequestService, private dataService: DataService, private datePipe: DatePipe, private router: Router) {}

  ngOnInit(): void {
    this.bloodRequestService.getAll().subscribe(response => {
      this.bloodRequests = response;
      this.dataSource = new MatTableDataSource(response);
    })
  }

  formatBloodType(bloodRequest: BloodRequest) {
    let bloodType = bloodRequest.bloodType.replace('_', '');
    bloodType = bloodType.indexOf('POSITIVE') === -1 ? bloodType.replace('NEGATIVE', '-') : bloodType.replace('POSITIVE', '+');

    return bloodType;
  }

  startDelivery(bloodRequest: BloodRequest) {
    this.dataService.bloodRequest = bloodRequest;
    this.router.navigate(['/location-simulator'], {
      state: { bloodRequest: bloodRequest }
    })
  }

}
