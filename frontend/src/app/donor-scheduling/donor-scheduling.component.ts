import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BloodDonor } from '../model/Users';
import { BloodDonorService } from '../services/BloodDonorService';
import { DonorSchedulingService } from '../services/DonorSchedulingService';
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
  public currentDonor : BloodDonor;
  public questionsFlag: Boolean;
  constructor(private formBuilder: FormBuilder,
    private appointmentService: DonorSchedulingService,
    private donorService: BloodDonorService) { }

  private today: Date = new Date();
  public queryForm: FormGroup = this.formBuilder.group({
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
  get dateTime() { return this.queryForm.get('dateTime'); }

  get dateTimeScheduled() { return this.schedule_form.get('dateTime'); }
  get durationScheduled() { return this.schedule_form.get('duration'); }


  ngOnInit(): void {
    this.queryForm.valueChanges.subscribe(x => {
          this.new_flag = false;
          this.banks = null;
      })
    this.donorService.getCurrentBloodDonor().subscribe(x =>{
      this.currentDonor = x;
      console.log(this.currentDonor);
      this.donorService.checkBloodDonorQuestionaire(this.currentDonor.personalId).subscribe(x=>{
        this.questionsFlag = x;
        console.log(this.questionsFlag);
      })
    })

  }
  checkBanks(): void{
    let item = this.queryForm.getRawValue();
    let date = new Date(item.dateTime);
    //"dd-MM-yyyy hh:mm:ss"

    const dto = {
      dateTime: ('0'+date.getDate()).slice(-2)+"-"+('0'+(date.getMonth()+1)).slice(-2)+"-"+date.getFullYear()+" "+('0'+date.getHours()).slice(-2)+":"+('0'+date.getMinutes()).slice(-2)+":00",
      duration: 30,
      sortCriteria: {
        direction: "desc",
        property: "averageGrade"
      }
    }
    this.appointmentService.getFreeBanks(dto).subscribe(res=>{
      this.banks = res;
    })
  }
  cardClick(item : any): void{
    this.schedule_form.get("dateTime").setValue(this.queryForm.get('dateTime').value);
    this.schedule_form.get("bloodBankId").setValue(item.id);
    this.schedule_form.get("bloodDonorId").setValue(this.currentDonor.personalId);

    this.new_flag = true;
    this.displayDate = this.schedule_form.get('dateTime').value;
    this.displayDuration = this.schedule_form.get('duration').value;
    this.displayBank = item.title;
  }
  schedule() :void{
    let item = this.schedule_form.getRawValue();
    let date = new Date(item.dateTime);
    //"dd-MM-yyyy hh:mm:ss"
    item.dateTime = ('0'+date.getDate()).slice(-2)+"-"+('0'+(date.getMonth()+1)).slice(-2)+"-"+date.getFullYear()+" "+('0'+date.getHours()).slice(-2)+":"+('0'+date.getMinutes()).slice(-2)+":00";
    console.log(item);
    this.appointmentService.createAppointment(item).subscribe(res=>{
      if(res == null){
        alert("Success");
      }
      else{
        alert(res.error);
      }
    });
  }
}
