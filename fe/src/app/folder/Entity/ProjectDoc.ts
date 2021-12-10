export class ProjectDoc {
  constructor(
    public projectDocId?: string,
    public projectId?: string,
    public name?: string,
    public action?: any,
    public folderId?: number,
    public isInFolder?: boolean,
  ) {}
}