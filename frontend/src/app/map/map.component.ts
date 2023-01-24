import { Component, OnInit } from '@angular/core';
import 'ol/ol.css';
import Map from 'ol/Map';
import View from 'ol/View';
import { OSM } from 'ol/source';
import TileLayer from 'ol/layer/Tile';
import {FullScreen} from "ol/control";
import {transform} from "ol/proj";
@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {
  public map!: Map
  ngOnInit(): void {
    this.map = new Map({
      layers: [
        new TileLayer({
          source: new OSM(),
        }),
      ],
      target: 'map',
      view: new View({
        center: transform(
          [19.82301951799475, 45.24008849556734],
          'EPSG:4326',
          'EPSG:3857'
        ),
        zoom: 14,
      }),
    });
    this.map.addControl(new FullScreen)

  }
}
