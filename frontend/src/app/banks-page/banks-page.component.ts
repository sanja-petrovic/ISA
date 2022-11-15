import {Component, OnInit} from '@angular/core';
import {BloodBankService} from "../services/BloodBankService";
import axios from 'axios';

@Component({
  selector: 'app-banks-page',
  templateUrl: './banks-page.component.html',
  styleUrls: ['./banks-page.component.css']
})
export class BanksPageComponent implements OnInit {

  public banks: any[] = [];
  public searchCriteria: string[] = ["", ""];
  public gradeFilter: string = "0";
  public sortFilter: string = "title-asc";

  getBanks(): void {
    this.bloodBankService.getAll().subscribe(data => {
      this.banks = data;
    })
  }

  searchBanks(): void {
    const sort: string[] = this.sortFilter.split('-');
    const dto = {
      searchCriteria: this.searchCriteria,
      filterGrade: this.gradeFilter,
      sortCriteria: {
        direction: sort[1],
        property: sort[0]
      }
    }
    this.bloodBankService.searchSort(dto).subscribe(data => {
      console.log(data);
      this.banks = data;
    })
  }

  constructor(private bloodBankService: BloodBankService) {
  }

  ngOnInit(): void {
    this.getBanks();
  }


}
