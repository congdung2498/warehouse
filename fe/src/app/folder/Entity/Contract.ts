import { ContractDoc } from "./ContractDoc";
import { Construction } from "./Construction";

export class Contract {
  constructor(
    public contractId?: string,
    public name?: string,
    public docs?: ContractDoc[],
    public constructions?: Construction[],
  ) {}
}