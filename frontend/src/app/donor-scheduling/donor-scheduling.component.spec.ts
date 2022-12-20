import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DonorSchedulingComponent } from './donor-scheduling.component';

describe('DonorSchedulingComponent', () => {
  let component: DonorSchedulingComponent;
  let fixture: ComponentFixture<DonorSchedulingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DonorSchedulingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DonorSchedulingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
