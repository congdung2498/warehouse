import { TestBed, inject } from '@angular/core/testing';

import {InOutEmployeeService} from "./inOutEmployeeService.service";

describe('InOutEmployeeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [InOutEmployeeService]
    });
  });

  it('should be created', inject([InOutEmployeeService], (service: InOutEmployeeService) => {
    expect(service).toBeTruthy();
  }));
});
