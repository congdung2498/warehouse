export class Place {
    constructor(
        public placeId: string,
        public placeName: string,
        public lstPlaceId?: string[],
        public username?: string,
        public securityUsername?: string,
        public securityPassword?: string
    ) { }
}