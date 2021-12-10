// app.po.ts
import { browser, element, by } from 'protractor';

export class AppPage {
  navigateTo() {
    // Navigate to the home page of the app
    return browser.get('/login/vebinh');
  }

  getUsernameLoginPage() {
    // Get the home page heading element reference
    return element(by.css('input.email'));
  }

  getPasswordLoginPage() {
    // Get the home page heading element reference
    return element(by.css('input.password'));
  }

  getButtonLoginPage() {
    // Get the home page heading element reference
    return element(by.css('button#login'));
  }

  getNavbar() {
    // Get nav bar
    return element(by.css('app-header div'));
  }
}
