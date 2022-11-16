import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicalStaffRegistrationComponent } from './medical-staff-registration.component';

describe('MedicalStaffRegistrationComponent', () => {
  let component: MedicalStaffRegistrationComponent;
  let fixture: ComponentFixture<MedicalStaffRegistrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MedicalStaffRegistrationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MedicalStaffRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
