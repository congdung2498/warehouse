import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestOfficeComponent } from './request-office.component';

describe('RequestOfficeComponent', () => {
  let component: RequestOfficeComponent;
  let fixture: ComponentFixture<RequestOfficeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RequestOfficeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RequestOfficeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
