import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LunchComponentComponent } from './lunch-component.component';

describe('LunchComponentComponent', () => {
  let component: LunchComponentComponent;
  let fixture: ComponentFixture<LunchComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LunchComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LunchComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
