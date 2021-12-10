import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FilebucketComponent } from './filebucket.component';

describe('FilebucketComponent', () => {
  let component: FilebucketComponent;
  let fixture: ComponentFixture<FilebucketComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FilebucketComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FilebucketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
