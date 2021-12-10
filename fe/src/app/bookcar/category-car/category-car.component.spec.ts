import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryCarComponent } from './category-car.component';

describe('CategoryCarComponent', () => {
  let component: CategoryCarComponent;
  let fixture: ComponentFixture<CategoryCarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CategoryCarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CategoryCarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
