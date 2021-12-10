import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportStationeryComponent } from './report-stationery.component';

describe('ReportStationeryComponent', () => {
  let component: ReportStationeryComponent;
  let fixture: ComponentFixture<ReportStationeryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportStationeryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportStationeryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
