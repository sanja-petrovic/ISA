import { Component, OnInit } from '@angular/core';
import {TrackingRequestService} from "../services/TrackingRequestService";

@Component({
  selector: 'app-location-simulator',
  templateUrl: './location-simulator.component.html',
  styleUrls: ['./location-simulator.component.css']
})
export class LocationSimulatorComponent implements OnInit {

  constructor(private trackingRequestService: TrackingRequestService) { }

  ngOnInit(): void {
  }

  start(): void {
    this.trackingRequestService.start().subscribe();
  }

}
