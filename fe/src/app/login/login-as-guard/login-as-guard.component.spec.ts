import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginAsGuardComponent } from './login-as-guard.component';

describe('LoginAsGuardComponent', () => {
  let component: LoginAsGuardComponent;
  let fixture: ComponentFixture<LoginAsGuardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginAsGuardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginAsGuardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
