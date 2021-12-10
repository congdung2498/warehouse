import { TestBed, inject } from '@angular/core/testing';

import { ReportserviceService } from './reportservice.service';

describe('ReportserviceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ReportserviceService]
    });
  });

  it('should be created', inject([ReportserviceService], (service: ReportserviceService) => {
    expect(service).toBeTruthy();
  }));
});
