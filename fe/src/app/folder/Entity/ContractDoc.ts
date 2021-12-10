export class ContractDoc {
  constructor(
    public contractDocId?: string,
    public name?: string,
    public action?: any,
    public folderId?: number,
    public isInFolder?: boolean,
  ) {}
}