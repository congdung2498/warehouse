
import {SelectItem} from "primeng/api";


export class DateTimeUtil {
  public static vn = {
    firstDayOfWeek: 1,
    dayNames: ['Chủ Nhật', 'Thứ hai', 'Thứ 3', 'Thứ 4', 'Thứ 5', 'Thứ 6', 'Thứ 7'],
    dayNamesShort: ['CN', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7'],
    dayNamesMin: ['CN', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7'],
    monthNames: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6',
      'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'],
    monthNamesShort: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6',
      'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'],
    today: 'Hôm nay',
    clear: 'Xóa'
  };

  public static months: Month[] = [
    {name: 1, value: 'Tháng 1'},
    {name: 2, value: 'Tháng 2'},
    {name: 3, value: 'Tháng 3'},
    {name: 4, value: 'Tháng 4'},
    {name: 5, value: 'Tháng 5'},
    {name: 6, value: 'Tháng 6'},
    {name: 7, value: 'Tháng 7'},
    {name: 8, value: 'Tháng 8'},
    {name: 9, value: 'Tháng 9'},
    {name: 10, value: 'Tháng 10'},
    {name: 11, value: 'Tháng 11'},
    {name: 12, value: 'Tháng 12'},
  ];

  public  static getMonthByName(month: number): Month {
    for(let i = 0; i < this.months.length; i++) {
      if(this.months[i].name === month) return this.months[i];
    }
    return null;
  }

  public static getDayOfNextMonth(): Date {
    let date = new Date();
    let day = date.getDate();
    let month = date.getMonth();
    let year = date.getFullYear();


    let lastDayOfNextYear = this.getLastDayOfMonth(date.getMonth() + 1);



    console.log(date);
    console.log(date.getDate());
    console.log(date.getMonth());
    console.log(date.getFullYear());
    return date;
  }

  static isLastDayOfMonth(date: Date): boolean {
    if(date.getDay() === this.getLastDayOfMonth(date.getMonth())) return true;
    return false;
  }

  static getLastDayOfMonth(month: number): number {
    if(month === 1 || month === 3 || month === 5 || month === 7 || month === 8 || month === 10 || month === 12) return 31;
    else if(month === 4 || month === 6 || month === 9 || month === 11) return 30;
    else if(this.isLeapYear()) return 29;
    else return 28;
  }

  static isLeapYear(): boolean {
    let date = new Date();
    if(date.getFullYear() % 4 === 0) return true;
    return false;
  }
}

export class Month {
  public name: number;
  public value: string;

  constructor() {}
}
