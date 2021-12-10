import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RattingListRequestComponent } from './ratting-list-request.component';

describe('RattingListRequestComponent', () => {
  let component: RattingListRequestComponent;
  let fixture: ComponentFixture<RattingListRequestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RattingListRequestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RattingListRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
