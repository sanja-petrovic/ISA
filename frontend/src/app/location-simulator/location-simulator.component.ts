import { Component, OnInit } from '@angular/core';
import {TrackingRequestService} from "../services/TrackingRequestService";
import {DataService} from "../services/DataService";
import {BloodRequest} from "../model/BloodRequest";

@Component({
  selector: 'app-location-simulator',
  templateUrl: './location-simulator.component.html',
  styleUrls: ['./location-simulator.component.css']
})
export class LocationSimulatorComponent implements OnInit {

  constructor(private trackingRequestService: TrackingRequestService, private dataService: DataService) { }
  bloodRequest: BloodRequest;
  ngOnInit(): void {
    this.bloodRequest = this.dataService.bloodRequest;
  }

  start(): void {
    this.trackingRequestService.start().subscribe();
  }

}
