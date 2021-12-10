import { TestBed, inject } from '@angular/core/testing';

import { BookcarService } from './bookcar.service';

describe('BookcarService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BookcarService]
    });
  });

  it('should be created', inject([BookcarService], (service: BookcarService) => {
    expect(service).toBeTruthy();
  }));
});
