import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicalStaffBloodBankComponent } from './medical-staff-blood-bank.component';

describe('MedicalStaffBloodBankComponent', () => {
  let component: MedicalStaffBloodBankComponent;
  let fixture: ComponentFixture<MedicalStaffBloodBankComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MedicalStaffBloodBankComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MedicalStaffBloodBankComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
