import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCardIdComponent } from './add-card-id.component';

describe('AddCarIdComponent', () => {
  let component: AddCardIdComponent;
  let fixture: ComponentFixture<AddCardIdComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddCardIdComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCardIdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
