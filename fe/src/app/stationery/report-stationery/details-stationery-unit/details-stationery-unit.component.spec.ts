import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailsStationeryUnitComponent } from './details-stationery-unit.component';

describe('DetailsStationeryUnitComponent', () => {
  let component: DetailsStationeryUnitComponent;
  let fixture: ComponentFixture<DetailsStationeryUnitComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetailsStationeryUnitComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailsStationeryUnitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
