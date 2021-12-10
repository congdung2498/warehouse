export class ConstructionDoc {
  constructor(
    public constructionDocId?: string,
    public name?: string,
    public action?: any,
    public folderId?: number,
    public isInFolder?: boolean,
  ) {}
}