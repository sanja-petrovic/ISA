import { Component, OnInit } from '@angular/core';
import {BloodRequest} from "../model/BloodRequest";
import {BloodRequestService} from "../services/BloodRequestService";

@Component({
  selector: 'app-blood-requests-list',
  templateUrl: './blood-requests-list.component.html',
  styleUrls: ['./blood-requests-list.component.css']
})
export class BloodRequestsListComponent implements OnInit {

  public bloodRequests: BloodRequest[];

  constructor(private bloodRequestService: BloodRequestService) {}

  ngOnInit(): void {
    this.bloodRequestService.getAll().subscribe(response => {
      this.bloodRequests = response;
    })
  }

}
