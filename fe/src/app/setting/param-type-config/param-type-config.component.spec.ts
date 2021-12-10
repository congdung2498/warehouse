import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ParamTypeConfigComponent } from './param-type-config.component';

describe('ParamTypeConfigComponent', () => {
  let component: ParamTypeConfigComponent;
  let fixture: ComponentFixture<ParamTypeConfigComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ParamTypeConfigComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ParamTypeConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
