import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessRequestStationeryComponent } from './process-request-stationery.component';

describe('ProcessRequestStationeryComponent', () => {
  let component: ProcessRequestStationeryComponent;
  let fixture: ComponentFixture<ProcessRequestStationeryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProcessRequestStationeryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcessRequestStationeryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
