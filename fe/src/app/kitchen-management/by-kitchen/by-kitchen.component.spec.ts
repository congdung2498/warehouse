import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ByKitchenComponent } from './by-kitchen.component';

describe('ByKitchenComponent', () => {
  let component: ByKitchenComponent;
  let fixture: ComponentFixture<ByKitchenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ByKitchenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ByKitchenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
