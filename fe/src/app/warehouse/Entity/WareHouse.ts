export class Warehouse{
    
    constructor (
        public warehouseId: Number,
        public name: String,
        public type: Number,
        public status: Number,
        public acreage: Number,
        public address: String,
        public row_num: Number,
        public column_num: Number,
        public height_num: Number,
    ) {}

}