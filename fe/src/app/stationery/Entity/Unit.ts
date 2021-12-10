export class Unit {
    constructor(
        public unitId: number,
        public unitName: string,
        public twoLevelUnit : string,
        public securityUsername?: string,
        public securityPassword?: string
    ) { }
}