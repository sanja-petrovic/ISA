import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ScheduleDurationValidator } from '../validators/ScheduleDurationValidator';
import { SchedulingDateValidator } from '../validators/SchedulingDateValidator';

@Component({
  selector: 'app-donor-scheduling',
  templateUrl: './donor-scheduling.component.html',
  styleUrls: ['./donor-scheduling.component.css']
})
export class DonorSchedulingComponent implements OnInit {

  constructor(private formBuilder: FormBuilder) { }
  private today: Date = new Date();
  public querry_form: FormGroup = this.formBuilder.group({
    dateTime: [this.today.toString(),[Validators.required,SchedulingDateValidator()]],
  });
  
  public schedule_form: FormGroup = this.formBuilder.group({
    dateTime: [this.today.toString(),[Validators.required,SchedulingDateValidator()]],
    bloodBankId: [null, [Validators.required]],
    bloodDonorId: null, 
    duration: [30,[Validators.required,ScheduleDurationValidator()]],
    id: null,
    status: "NOT_SCHEDULED",
  });

  get dateTime() { return this.querry_form.get('dateTime'); }

  ngOnInit(): void {
  }
  checkBanks(): void{
    
  }
}
