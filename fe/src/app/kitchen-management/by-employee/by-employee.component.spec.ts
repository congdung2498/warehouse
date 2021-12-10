import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ByEmployeeComponent } from './by-employee.component';

describe('ByEmployeeComponent', () => {
  let component: ByEmployeeComponent;
  let fixture: ComponentFixture<ByEmployeeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ByEmployeeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ByEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
