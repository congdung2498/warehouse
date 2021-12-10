import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportBookcarComponent } from './report-bookcar.component';

describe('ReportBookcarComponent', () => {
  let component: ReportBookcarComponent;
  let fixture: ComponentFixture<ReportBookcarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportBookcarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportBookcarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
