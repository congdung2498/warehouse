import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestStationeryComponent } from './request-stationery.component';

describe('RequestStationeryComponent', () => {
  let component: RequestStationeryComponent;
  let fixture: ComponentFixture<RequestStationeryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RequestStationeryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RequestStationeryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
