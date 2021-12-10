import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VersionConfigComponent } from './version-config.component';

describe('VersionConfigComponent', () => {
  let component: VersionConfigComponent;
  let fixture: ComponentFixture<VersionConfigComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VersionConfigComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VersionConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
