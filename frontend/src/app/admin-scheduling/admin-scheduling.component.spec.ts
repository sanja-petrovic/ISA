import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminSchedulingComponent } from './admin-scheduling.component';

describe('AdminSchedulingComponent', () => {
  let component: AdminSchedulingComponent;
  let fixture: ComponentFixture<AdminSchedulingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminSchedulingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminSchedulingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
