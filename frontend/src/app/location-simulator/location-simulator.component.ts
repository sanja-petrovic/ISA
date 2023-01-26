import { Component, OnInit } from '@angular/core';
import {TrackingRequestService} from "../services/TrackingRequestService";
import {DataService} from "../services/DataService";
import {BloodRequest} from "../model/BloodRequest";
import {Route, Router} from "@angular/router";
import {latLng, LayerGroup, marker, tileLayer, icon} from "leaflet";
import {LocationUpdate} from "../model/LocationUpdate";
import * as SockJS from "sockjs-client";
import * as Stomp from "stompjs";

@Component({
  selector: 'app-location-simulator',
  templateUrl: './location-simulator.component.html',
  styleUrls: ['./location-simulator.component.css']
})
export class LocationSimulatorComponent implements OnInit {
  options = {
    layers: [
      tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 18,
        attribution: '...',
      }),
    ],
    zoom: 10,
    center: latLng(45.253434, 19.831323),
  };
  mainGroup: LayerGroup[] = [];
  private stompClient: any;
  private bloodRequest: BloodRequest;
  private currentLocation: LocationUpdate;
  private icon: string =  "../../assets/images/location.png";
  constructor(private trackingRequestService: TrackingRequestService, private router: Router) {
    this.bloodRequest = this.router.getCurrentNavigation().extras.state['bloodRequest'];
  }
  ngOnInit(): void {
  }

  start(): void {
    this.initializeWebSocketConnection();
    this.trackingRequestService.start(this.bloodRequest).subscribe();
  }

  initializeWebSocketConnection() {
    alert("initializing web socket connection");
    let ws = new SockJS('http://localhost:8080/api/socket');
    this.stompClient = Stomp.over(ws);
    this.stompClient.debug = null;
    let that = this;
    this.stompClient.connect({}, function () {
      alert("initializing web socket connection2");
      that.openGlobalSocket();
    });
  }

  openGlobalSocket() {
    alert("initializing web socket connection3");
    let geoLayerRouteGroup: LayerGroup = new LayerGroup();
    this.stompClient.subscribe(`/location-update/${this.bloodRequest.id}`, (message: { body: string }) => {
      alert(message.body);
      let locationUpdate: LocationUpdate = JSON.parse(message.body);
      let markerLayer = marker([locationUpdate.longitude, locationUpdate.latitude], {
        icon: icon({
          iconUrl: this.icon,
          iconSize: [45, 45],
          iconAnchor: [18, 45],
        }),
      });
      markerLayer.addTo(geoLayerRouteGroup);
      this.mainGroup = [...this.mainGroup, geoLayerRouteGroup];
    });
  }

}
