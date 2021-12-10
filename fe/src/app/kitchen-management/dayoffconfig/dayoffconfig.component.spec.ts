import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DayoffconfigComponent } from './dayoffconfig.component';

describe('DayoffconfigComponent', () => {
  let component: DayoffconfigComponent;
  let fixture: ComponentFixture<DayoffconfigComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DayoffconfigComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DayoffconfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
