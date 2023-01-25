import { Component, OnInit } from '@angular/core';
import { latLng, tileLayer, marker, geoJSON, LayerGroup, icon } from 'leaflet';
import * as Stomp from 'stompjs';
import * as SockJS from "sockjs-client";
import {BloodRequestService} from "../services/BloodRequestService";
import {BloodRequest} from "../model/BloodRequest";
import {LocationUpdate} from "../model/LocationUpdate";

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css'],
})
export class MapComponent implements OnInit {
  options = {
    layers: [
      tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 18,
        attribution: '...',
      }),
    ],
    zoom: 14,
    center: latLng(45.253434, 19.831323),
  };
  mainGroup: LayerGroup[] = [];
  private stompClient: any;
  private bloodRequests: BloodRequest[];
  private bloodRequest: BloodRequest;

  constructor(private bloodRequestService: BloodRequestService) {}

  ngOnInit(): void {
    this.bloodRequestService.getAll().subscribe(response => {
      this.bloodRequests = response;
      this.bloodRequest = response[0];
    })
    this.initializeWebSocketConnection();
  }

  initializeWebSocketConnection() {
    let ws = new SockJS('http://localhost:8080/api/socket');
    this.stompClient = Stomp.over(ws);
    this.stompClient.debug = null;
    let that = this;
    this.stompClient.connect({}, function () {
    });
  }

  openGlobalSocket() {
    this.stompClient.subscribe(`/api/location-update/${this.bloodRequest.id}`, (message: { body: string }) => {
      let locationUpdate: LocationUpdate = JSON.parse(message.body);
    });
  }
}
