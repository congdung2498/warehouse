export class PackageDoc {
  constructor(
    public packageDocId?: string,
    public name?: string,
    public action?: any,
    public folderId?: number,
    public isInFolder?: boolean,
  ) {}
}