import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DonorSchedulingService } from '../services/donor-scheduling.service';
import { ScheduleDurationValidator } from '../validators/ScheduleDurationValidator';
import { SchedulingDateValidator } from '../validators/SchedulingDateValidator';

@Component({
  selector: 'app-donor-scheduling',
  templateUrl: './donor-scheduling.component.html',
  styleUrls: ['./donor-scheduling.component.css']
})
export class DonorSchedulingComponent implements OnInit {
  public banks: any[] = [];
  public new_flag = false;
  public displayDate: String = "";
  public displayDuration: String = "";
  public displayBank: String = "";

  constructor(private formBuilder: FormBuilder,
    private appointmentService: DonorSchedulingService) { }
  
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

  get dateTimeScheduled() { return this.schedule_form.get('dateTime'); }
  get durationScheduled() { return this.schedule_form.get('duration'); }
  

  ngOnInit(): void {
    this.querry_form.valueChanges.subscribe(x => {
          this.new_flag = false;
          this.banks = null;
      })
  }
  checkBanks(): void{
    let item = this.querry_form.getRawValue();
    let date = new Date(item.dateTime);
    //"dd-MM-yyyy hh:mm:ss"
    
    const dto = {
      dateTime: ('0'+date.getDate()).slice(-2)+"-"+('0'+(date.getMonth()+1)).slice(-2)+"-"+date.getFullYear()+" "+('0'+date.getHours()).slice(-2)+":"+('0'+date.getMinutes()).slice(-2)+":00",
      duration: 30,
      sortCriteria: {
        direction: "asc",
        property: "averageGrade"
      }
    }
    console.log(dto);
    this.appointmentService.checkBanks(dto).subscribe(res=>{
      this.banks = res;
    })
  }
  cardClick(item : any): void{
    console.log(item);
    this.schedule_form.get("dateTime").setValue(this.querry_form.get('dateTime').value);
    this.schedule_form.get("bloodBankId").setValue(item.id);
    console.log(this.schedule_form.getRawValue());
    this.new_flag = true;
    this.displayDate = this.schedule_form.get('dateTime').value;
    this.displayDuration = this.schedule_form.get('duration').value;
    this.displayBank = item.title;
  }
  shedule() :void{

  }
}
