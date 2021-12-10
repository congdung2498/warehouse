import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TinboxComponent } from './tinbox.component';

describe('TinboxComponent', () => {
  let component: TinboxComponent;
  let fixture: ComponentFixture<TinboxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TinboxComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TinboxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
