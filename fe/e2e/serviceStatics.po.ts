// app.po.ts
import { browser, element, by } from 'protractor';

export class ServiceStatics {
  getHeader() {
    return element(by.css('h1'));
  }
  getFieldSet() {
    return element(by.css('p-fieldset'));
  }
  getInputField() {
    return element.all(by.css('div.row.col-sm-12'));
  }
}
