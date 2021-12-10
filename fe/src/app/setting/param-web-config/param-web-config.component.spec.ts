import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ParamWebConfigComponent } from './param-web-config.component';

describe('ParamWebConfigComponent', () => {
  let component: ParamWebConfigComponent;
  let fixture: ComponentFixture<ParamWebConfigComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ParamWebConfigComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ParamWebConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
