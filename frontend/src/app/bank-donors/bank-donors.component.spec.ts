import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BankDonorsComponent } from './bank-donors.component';

describe('BankDonorsComponent', () => {
  let component: BankDonorsComponent;
  let fixture: ComponentFixture<BankDonorsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BankDonorsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BankDonorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
