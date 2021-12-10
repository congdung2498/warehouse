export class Location {
  constructor(
    public placeId: number,
    public placeCode: string,
    public placeName: string,
    public area: string,
    public description: string,
    public status: number
  ) {}
}
