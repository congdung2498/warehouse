import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ParamUnitConfigComponent } from './param-unit-config.component';

describe('ParamUnitConfigComponent', () => {
  let component: ParamUnitConfigComponent;
  let fixture: ComponentFixture<ParamUnitConfigComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ParamUnitConfigComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ParamUnitConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
