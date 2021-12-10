import { ConstructionDoc } from "./ConstructionDoc";

export class Construction {
  constructor(
    public constructionId?: string,
    public name?: string,
    public docs?: ConstructionDoc[],
  ) {}
}