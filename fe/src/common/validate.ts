
import {AutoComplete, Calendar, Dropdown} from "primeng/primeng";
import {AppComponent} from "../app/app.component";
import {ElementRef} from "@angular/core";


export class ValidateUtils {

  public static validate(field: Dropdown, message: string, app: AppComponent) {
    if(!field) return ;
    field.focus();
    app.showWarn(message);
  }

  public static validateTime(field: Calendar, message: string, app: AppComponent) {
    if(!field) return ;
    field.domHandler.findSingle(field.el.nativeElement, 'input').focus();
    app.showWarn(message);
  }

  public static validateAutoComplete(field: AutoComplete, message: string, app: AppComponent) {
    if(!field) return ;
    field.domHandler.findSingle(field.el.nativeElement, 'input').focus();
    app.showWarn(message);
  }

  public static validateTextInput(field: ElementRef, message: string, app: AppComponent) {
    if(!field) return ;
    field.nativeElement.focus();
    app.showWarn(message);
  }

}
