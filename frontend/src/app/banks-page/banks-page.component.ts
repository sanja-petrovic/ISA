import { Component, OnInit } from '@angular/core';
import {BloodBankService} from "../services/BloodBankService";
import axios from 'axios';
@Component({
  selector: 'app-banks-page',
  templateUrl: './banks-page.component.html',
  styleUrls: ['./banks-page.component.css']
})
export class BanksPageComponent implements OnInit {

  public banks: any[] = [];

  getBanks(): void {
    axios.get('http://localhost:8080/bloodbanks').then((response) => {
      this.banks = response.data?.bloodBanks;
      console.log(response.data);
    })
  }

  constructor(private bloodBankService: BloodBankService) { }

  ngOnInit(): void {
      this.getBanks();
  }


}
