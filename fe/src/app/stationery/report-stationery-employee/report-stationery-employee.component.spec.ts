import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportStationeryEmployeeComponent } from './report-stationery-employee.component';

describe('ReportStationeryEmployeeComponent', () => {
  let component: ReportStationeryEmployeeComponent;
  let fixture: ComponentFixture<ReportStationeryEmployeeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportStationeryEmployeeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportStationeryEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
