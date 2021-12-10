import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmPendingComponent } from './confirm-pending.component';

describe('ConfirmPendingComponent', () => {
  let component: ConfirmPendingComponent;
  let fixture: ComponentFixture<ConfirmPendingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfirmPendingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmPendingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
