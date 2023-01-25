import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BloodRequestsListComponent } from './blood-requests-list.component';

describe('BloodRequestsListComponent', () => {
  let component: BloodRequestsListComponent;
  let fixture: ComponentFixture<BloodRequestsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BloodRequestsListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BloodRequestsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
