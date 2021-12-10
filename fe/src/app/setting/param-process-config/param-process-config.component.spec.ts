import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ParamProcessConfigComponent } from './param-process-config.component';

describe('ParamProcessConfigComponent', () => {
  let component: ParamProcessConfigComponent;
  let fixture: ComponentFixture<ParamProcessConfigComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ParamProcessConfigComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ParamProcessConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
