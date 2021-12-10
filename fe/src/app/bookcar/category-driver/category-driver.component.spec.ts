import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryDriverComponent } from './category-driver.component';

describe('CategoryDriverComponent', () => {
  let component: CategoryDriverComponent;
  let fixture: ComponentFixture<CategoryDriverComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CategoryDriverComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CategoryDriverComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
