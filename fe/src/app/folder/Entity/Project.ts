import { ProjectDoc } from "./ProjectDoc";
import { Package } from "./Package";
import { Contract } from "./Contract";

export class Project {
  constructor(
    public projectId?: string,
    public name?: string,    
    public code?: string,
    public docs?: ProjectDoc[],
    public packages?: Package[],
    public contracts?: Contract[],
    public description?: string,
    public erpId?: string,
  ) {}
}
