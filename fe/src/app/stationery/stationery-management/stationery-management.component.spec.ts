import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StationeryManagementComponent } from './stationery-management.component';

describe('StationeryManagementComponent', () => {
  let component: StationeryManagementComponent;
  let fixture: ComponentFixture<StationeryManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StationeryManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StationeryManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
