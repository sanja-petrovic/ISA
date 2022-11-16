import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Address } from '../model/Address';
import { BloodBank } from '../model/BloodBank';
import { BloodBankService } from '../services/BloodBankService';

@Component({
  selector: 'app-bank-registration',
  templateUrl: './bank-registration.component.html',
  styleUrls: ['./bank-registration.component.css']
})
export class BankRegistrationComponent implements OnInit {
  bloodBank : any ;
  constructor( private bloodBankService : BloodBankService, private router:Router , private formBuilder: FormBuilder, ) { }
  form: FormGroup = this.formBuilder.group({
   title : [''],
   description :[''],
   workingHoursStart : [''],
   workingHoursEnd : [''],
   street : [''],
   city : [''],
   country : ['']
  });
  ngOnInit(): void {
  }
  public createBloodBank() {
    if (!this.isValidInput()) return;
    this.bloodBankService.createBloodBank(this.form.value).subscribe(res => {
      this.router.navigate(['/banks']);
    });
  }

  private isValidInput(): boolean {
    return ((this.bloodBank?.title != '') || (this.bloodBank?.description != ''));
  }

}
