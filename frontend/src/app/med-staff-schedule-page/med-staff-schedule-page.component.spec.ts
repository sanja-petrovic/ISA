import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedStaffSchedulePageComponent } from './med-staff-schedule-page.component';

describe('MedStaffSchedulePageComponent', () => {
  let component: MedStaffSchedulePageComponent;
  let fixture: ComponentFixture<MedStaffSchedulePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MedStaffSchedulePageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MedStaffSchedulePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
