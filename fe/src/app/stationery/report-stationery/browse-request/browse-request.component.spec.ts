import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BrowseRequestComponent } from './browse-request.component';

describe('BrowseRequestComponent', () => {
  let component: BrowseRequestComponent;
  let fixture: ComponentFixture<BrowseRequestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BrowseRequestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BrowseRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
