import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessUnitComponent } from './process-unit.component';

describe('ProcessUnitComponent', () => {
  let component: ProcessUnitComponent;
  let fixture: ComponentFixture<ProcessUnitComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProcessUnitComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcessUnitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
