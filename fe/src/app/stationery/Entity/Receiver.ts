export class Receiver {
    constructor(public receiverId: string,
                public employeeId: string,
                public place: string,
                public unit: string,
                public userName: string,
                public fullName: string,
                public employeePhone: string,
                public roleId: string,
                public roleName: string,
                public jobCode: string,
                public placeId: string,
                public unitId: string,
                public threeLevelUnit: string,
                public  placeNames: string,
                public  placeIds: string,
                public lstPlaceId: Array<Number>,
                public path: string,
                public listUnit: any,
                public username?: string,
                public savedUsername?: string,
                public securityUsername?: string,
                public securityPassword?: string) {
    }
}