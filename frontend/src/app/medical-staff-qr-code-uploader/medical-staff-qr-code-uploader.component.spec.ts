import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicalStaffQrCodeUploaderComponent } from './medical-staff-qr-code-uploader.component';

describe('MedicalStaffQrCodeUploaderComponent', () => {
  let component: MedicalStaffQrCodeUploaderComponent;
  let fixture: ComponentFixture<MedicalStaffQrCodeUploaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MedicalStaffQrCodeUploaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MedicalStaffQrCodeUploaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
