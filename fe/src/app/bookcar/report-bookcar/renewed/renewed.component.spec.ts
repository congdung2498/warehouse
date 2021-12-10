import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RenewedComponent} from './renewed.component';

describe('RenewedComponent', () => {
  let component: RenewedComponent;
  let fixture: ComponentFixture<RenewedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RenewedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RenewedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
