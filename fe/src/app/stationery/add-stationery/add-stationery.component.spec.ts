import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddStationeryComponent } from './add-stationery.component';

describe('AddStationeryComponent', () => {
  let component: AddStationeryComponent;
  let fixture: ComponentFixture<AddStationeryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddStationeryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddStationeryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
