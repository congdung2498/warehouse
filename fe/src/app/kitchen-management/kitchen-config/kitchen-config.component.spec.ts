import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KitchenConfigComponent } from './kitchen-config.component';

describe('KitchenConfigComponent', () => {
  let component: KitchenConfigComponent;
  let fixture: ComponentFixture<KitchenConfigComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KitchenConfigComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KitchenConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
