import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CarFreeTimeComponent } from './car-free-time.component';

describe('CarFreeTimeComponent', () => {
  let component: CarFreeTimeComponent;
  let fixture: ComponentFixture<CarFreeTimeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CarFreeTimeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CarFreeTimeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
