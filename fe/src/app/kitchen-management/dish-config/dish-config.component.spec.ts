import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DishConfigComponent } from './dish-config.component';

describe('DishConfigComponent', () => {
  let component: DishConfigComponent;
  let fixture: ComponentFixture<DishConfigComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DishConfigComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DishConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
