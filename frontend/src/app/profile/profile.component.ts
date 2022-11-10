import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { PatientService } from '../services/patient.service';
import { Patient } from '../model/Users';
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  profileForm: FormGroup = this.formBuilder.group({
    email: [],
    password: [],
    firstName: [],
    lastName: [],
    occupation: [],
    institution: [],
    gender: [],
    phoneNumber: [],
    personalId: [],
    homeAddress: [],
    city: [],
    country: [],
  });
  private patient : Patient;
  public editEnabled : boolean = false;
  constructor(
    private formBuilder: FormBuilder,
    private patientService : PatientService
    ) { }

  ngOnInit(): void {
    this.patientService.getPatient('supersus1').subscribe((res: any)=>{
      this.patient = res;
      this.profileForm.setValue(this.patient);
    })
    this.profileForm.get('personalId').disable();
  }
  saveEditChanges() :void{
    this.patientService.updatePatient(this.profileForm.getRawValue()).subscribe((res: any)=>{
      console.log(res);
    })
  }
  editClicked(): void{
    this.editEnabled = !this.editEnabled;
    if(this.editEnabled){
      this.profileForm.get('personalId').enable();
    }
    else{
      this.profileForm.get('personalId').disable();
    }
  }
}
