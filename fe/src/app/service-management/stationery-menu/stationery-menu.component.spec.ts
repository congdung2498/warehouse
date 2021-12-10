import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StationeryMenuComponent } from './stationery-menu.component';

describe('StationeryMenuComponent', () => {
  let component: StationeryMenuComponent;
  let fixture: ComponentFixture<StationeryMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StationeryMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StationeryMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
