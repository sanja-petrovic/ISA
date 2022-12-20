import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AdminSchedulingService} from '../services/AdminSchedulingService';
import { BloodBankService } from '../services/BloodBankService';
import { ScheduleDurationValidator } from '../validators/ScheduleDurationValidator';
import { SchedulingDateValidator } from '../validators/SchedulingDateValidator';

@Component({
  selector: 'app-admin-scheduling',
  templateUrl: './admin-scheduling.component.html',
  styleUrls: ['./admin-scheduling.component.css']
})
export class AdminSchedulingComponent implements OnInit {
  public banks: any[] = [];
  constructor(private bankService: BloodBankService,
              private formBuilder: FormBuilder,
              private router: Router,
              private appointmentService: AdminSchedulingService) { }
  private today: Date = new Date();
  public form: FormGroup = this.formBuilder.group({
    dateTime: [this.today.toString(),[Validators.required,SchedulingDateValidator()]],
    bloodBankId: [null, [Validators.required]],
    bloodDonorId: null,
    duration: [30,[Validators.required,ScheduleDurationValidator()]],
    id: null,
    status: "NOT_SCHEDULED",
  });

  ngOnInit(): void {
    this.bankService.getAll().subscribe(res=>{
      this.banks = res;
    })
  }
  onSubmit(): void {
    let item = this.form.getRawValue();
    let date = new Date(item.dateTime);
    //"dd-MM-yyyy hh:mm:ss"
    item.dateTime = ('0'+date.getDate()).slice(-2)+"-"+('0'+(date.getMonth()+1)).slice(-2)+"-"+date.getFullYear()+" "+('0'+date.getHours()).slice(-2)+":"+('0'+date.getMinutes()).slice(-2)+":00";
    this.appointmentService.createAppointment(item).subscribe(res=>{
      if(res == null){
        alert("Success");
      }
      else{
        alert(res.error);
      }
    });
  }

  get dateTime() { return this.form.get('dateTime'); }
  get duration() { return this.form.get('duration'); }
}
