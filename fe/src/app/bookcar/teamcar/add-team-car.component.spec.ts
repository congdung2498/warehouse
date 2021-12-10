import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddTeamCarComponent } from './add-team-car.component';

describe('AddTeamCarComponent', () => {
  let component: AddTeamCarComponent;
  let fixture: ComponentFixture<AddTeamCarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddTeamCarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddTeamCarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
