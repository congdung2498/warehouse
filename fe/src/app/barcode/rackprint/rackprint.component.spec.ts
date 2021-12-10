import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RackprintComponent } from './rackprint.component';

describe('RackprintComponent', () => {
  let component: RackprintComponent;
  let fixture: ComponentFixture<RackprintComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RackprintComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RackprintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
