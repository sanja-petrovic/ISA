import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Address } from '../model/Address';
import { BloodBank } from '../model/BloodBank';
import { BloodBankRegistrationDto } from '../model/BloodBankRegistrationDto';
import { BloodBankService } from '../services/BloodBankService';

@Component({
  selector: 'app-bank-registration',
  templateUrl: './bank-registration.component.html',
  styleUrls: ['./bank-registration.component.css']
})
export class BankRegistrationComponent implements OnInit {
  bloodBank : BloodBankRegistrationDto = new BloodBankRegistrationDto();
  constructor( private bloodBankService : BloodBankService, private router: Router ) { }

  ngOnInit(): void {
  }
  public createBloodBank() {
    if (!this.isValidInput()) return;
    this.bloodBankService.createBloodBank(this.bloodBank).subscribe(res => {
      this.router.navigate(['/bloodbanks']);
    });
  }

  private isValidInput(): boolean {
    return ((this.bloodBank?.title != '') || (this.bloodBank?.description != ''));
  }

}
