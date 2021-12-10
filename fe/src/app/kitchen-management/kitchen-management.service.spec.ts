import { TestBed, inject } from '@angular/core/testing';

import { KitchenManagementService } from './kitchen-management.service';

describe('KitchenManagementService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [KitchenManagementService]
    });
  });

  it('should be created', inject([KitchenManagementService], (service: KitchenManagementService) => {
    expect(service).toBeTruthy();
  }));
});
