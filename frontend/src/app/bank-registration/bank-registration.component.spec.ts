import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BankRegistrationComponent } from './bank-registration.component';

describe('BankRegistrationComponent', () => {
  let component: BankRegistrationComponent;
  let fixture: ComponentFixture<BankRegistrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BankRegistrationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BankRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
