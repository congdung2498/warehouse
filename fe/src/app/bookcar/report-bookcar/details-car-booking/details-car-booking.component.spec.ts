import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailsCarBookingComponent } from './details-car-booking.component';

describe('DetailsCarBookingComponent', () => {
  let component: DetailsCarBookingComponent;
  let fixture: ComponentFixture<DetailsCarBookingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetailsCarBookingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailsCarBookingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
