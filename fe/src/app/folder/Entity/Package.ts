import { PackageDoc } from "./PackageDoc";

export class Package {
  constructor(
    public packageId?: string,
    public name?: string,
    public docs?: PackageDoc[],
  ) {}
}