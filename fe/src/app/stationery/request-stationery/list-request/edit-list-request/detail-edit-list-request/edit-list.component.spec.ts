import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditListRequestComponent } from './edit-list-request.component';

describe('EditListRequestComponent', () => {
  let component: EditListRequestComponent;
  let fixture: ComponentFixture<EditListRequestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditListRequestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditListRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
