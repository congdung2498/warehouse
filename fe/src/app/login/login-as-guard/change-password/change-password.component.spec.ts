import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangePassWordComponent } from './change-password.component';

describe('ChangePassWordComponent', () => {
  let component: ChangePassWordComponent;
  let fixture: ComponentFixture<ChangePassWordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChangePassWordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangePassWordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
