export class DriveSquad {
  constructor(
    public squadId: string,
    public squadName: string,
    public placeId: number,
    public placeName: string,
    public empUsername: string,
    public empFullName: string,
    public status: number,
    public displayOption : string,
    public securityUsername?: string,
    public securityPassword?: string
  ) { }
}
