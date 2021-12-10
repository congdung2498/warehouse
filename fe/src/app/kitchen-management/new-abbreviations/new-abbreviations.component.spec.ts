import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewAbbreviationsComponent } from './new-abbreviations.component';

describe('NewAbbreviationsComponent', () => {
  let component: NewAbbreviationsComponent;
  let fixture: ComponentFixture<NewAbbreviationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewAbbreviationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewAbbreviationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
