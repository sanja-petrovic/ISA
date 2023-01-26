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
    zoom: 8,
    center: latLng(45.253434, 19.831323),
  };
  mainGroup: LayerGroup[] = [];
  originalMainGroup: LayerGroup[] = [];
  private stompClient: any;
  private bloodRequest: BloodRequest;
  private currentLocation: LocationUpdate;
  private vehicle = null
  private icon: string =  "../../assets/images/location.png";
  constructor(private trackingRequestService: TrackingRequestService, private router: Router) {
    this.bloodRequest = this.router.getCurrentNavigation().extras.state['bloodRequest'];
  }
  ngOnInit(): void {
    let geoLayerRouteGroup: LayerGroup = new LayerGroup();
    let markerHospital = marker([44.797731767760375, 20.458045885543164], {
      icon: icon({
        iconUrl: "../../assets/images/hospital.png",
        iconSize: [45, 45],
        iconAnchor: [18, 45],
      }),
    });
    let markerBloodBank = marker([this.bloodRequest.bloodBank.latitude, this.bloodRequest.bloodBank.longitude], {
      icon: icon({
        iconUrl: "../../assets/images/bloodbank.png",
        iconSize: [45, 45],
        iconAnchor: [18, 45],
      }),
    });
    markerHospital.addTo(geoLayerRouteGroup);
    markerBloodBank.addTo(geoLayerRouteGroup);
    this.mainGroup = [...this.mainGroup, geoLayerRouteGroup];
  }

  start(): void {
    this.initializeWebSocketConnection();
    this.trackingRequestService.start(this.bloodRequest).subscribe();
  }

  initializeWebSocketConnection() {
    let ws = new SockJS('http://localhost:8080/api/socket');
    this.stompClient = Stomp.over(ws);
    this.stompClient.debug = null;
    let that = this;
    this.stompClient.connect({}, function () {
      that.openGlobalSocket();
    });
  }

  openGlobalSocket() {
    let geoLayerRouteGroup: LayerGroup = new LayerGroup();
    let markerLayer = marker([this.bloodRequest.bloodBank.latitude, this.bloodRequest.bloodBank.longitude], {
      icon: icon({
        iconUrl: this.icon,
        iconSize: [35, 35],
        iconAnchor: [18, 35],
      }),
    });
    this.vehicle = markerLayer;
    markerLayer.addTo(geoLayerRouteGroup);
    this.mainGroup = [...this.mainGroup, geoLayerRouteGroup];
    this.stompClient.subscribe(`/location-update/${this.bloodRequest.id}`, (message: { body: string }) => {
      let locationUpdate: LocationUpdate = JSON.parse(message.body);
        this.vehicle.setLatLng([locationUpdate.latitude, locationUpdate.longitude])
        this.vehicle.update();
    });
  }

}
