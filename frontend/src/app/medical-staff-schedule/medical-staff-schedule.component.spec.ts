import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicalStaffScheduleComponent } from './medical-staff-schedule.component';

describe('MedicalStaffScheduleComponent', () => {
  let component: MedicalStaffScheduleComponent;
  let fixture: ComponentFixture<MedicalStaffScheduleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MedicalStaffScheduleComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MedicalStaffScheduleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
