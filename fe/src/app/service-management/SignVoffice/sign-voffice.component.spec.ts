import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import {SignVofficeComponent} from './sign-voffice.component';

describe('SignVofficeComponent', () => {
  let component: SignVofficeComponent;
  let fixture: ComponentFixture<SignVofficeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SignVofficeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
