import { Component, OnInit } from '@angular/core';
import { latLng, tileLayer, marker, geoJSON, LayerGroup, icon } from 'leaflet';
import * as Stomp from 'stompjs';
import * as SockJS from "sockjs-client";
import {BloodRequestService} from "../services/BloodRequestService";
import {BloodRequest} from "../model/BloodRequest";
import {LocationUpdate} from "../model/LocationUpdate";
import {DataService} from "../services/DataService";

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
    zoom: 10,
    center: latLng(45.253434, 19.831323),
  };
  mainGroup: LayerGroup[] = [];
  private stompClient: any;
  private bloodRequest: BloodRequest;
  private currentLocation: LocationUpdate;
  private icon: string =  "../../assets/images/location.png";

  constructor(private bloodRequestService: BloodRequestService, private dataService: DataService) {}

  ngOnInit(): void {
    this.bloodRequest = this.dataService.bloodRequest;
  }

  start(): void {
    this.initializeWebSocketConnection();
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
