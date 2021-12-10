import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VoteVptctComponent } from './vote-vptct.component';

describe('VoteVptctComponent', () => {
  let component: VoteVptctComponent;
  let fixture: ComponentFixture<VoteVptctComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VoteVptctComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VoteVptctComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
