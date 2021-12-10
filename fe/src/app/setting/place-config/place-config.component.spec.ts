import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaceConfigComponent } from './place-config.component';

describe('PlaceConfigComponent', () => {
  let component: PlaceConfigComponent;
  let fixture: ComponentFixture<PlaceConfigComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlaceConfigComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlaceConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
