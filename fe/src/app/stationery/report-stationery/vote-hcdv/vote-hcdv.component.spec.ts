import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VoteHcdvComponent } from './vote-hcdv.component';

describe('VoteHcdvComponent', () => {
  let component: VoteHcdvComponent;
  let fixture: ComponentFixture<VoteHcdvComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VoteHcdvComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VoteHcdvComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
