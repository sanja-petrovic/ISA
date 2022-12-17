import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DonorsAppointmentsComponent } from './donors-appointments.component';

describe('DonorsAppointmentsComponent', () => {
  let component: DonorsAppointmentsComponent;
  let fixture: ComponentFixture<DonorsAppointmentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DonorsAppointmentsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DonorsAppointmentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
