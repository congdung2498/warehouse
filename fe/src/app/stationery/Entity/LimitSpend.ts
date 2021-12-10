export class LimitSpend {
    constructor(
        public limitId: string,
        public limitValue: number,
        public securityUsername?: string,
        public securityPassword?: string
    ) { }
}