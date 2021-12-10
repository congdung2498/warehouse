import { AppPage } from './app.po';
import { browser, element, by, ElementFinder } from 'protractor';
import {} from 'jasmine';
import { ServiceStatics } from './serviceStatics.po';

export async function waitTillDisplayed (hopingElement: ElementFinder, timeout: number = 20000): Promise<boolean> {
  const result = await browser.wait(() => hopingElement.isPresent(), timeout);
  if (!result) {
    return false;
  }
  return await browser.wait(hopingElement.isDisplayed(), timeout);
}

describe('ng5-created the App and login success', function() {
  let page: AppPage;
  beforeEach(() => {
    page = new AppPage();
  });

  it('should redirect when success login', () => {
    page.navigateTo();
    page.getUsernameLoginPage().sendKeys('ANV03');
    page.getPasswordLoginPage().sendKeys('ANV03');
    page.getButtonLoginPage().click();
    expect<any>(browser.getCurrentUrl()).toEqual('http://localhost:49152/');
  });

  it('should should slice navbar upto 300px', () => {
    browser.waitForAngular();
    browser.actions().mouseMove(page.getNavbar()).perform();
    browser.driver.sleep(3000);
    expect<any>(page.getNavbar().getCssValue('width')).toEqual('300px');
  });
});

describe('test case 01', function() {
  let page: AppPage;
  let serviceStatistics: ServiceStatics;
  beforeEach(() => {
    page = new AppPage();
    serviceStatistics = new ServiceStatics();
  });

  it('should redirect to Thống kê yêu cầu dịch vụ page', () => {
    browser.waitForAngular();
    browser.actions().mouseMove(page.getNavbar()).perform();
    browser.driver.sleep(2000);
    expect<any>(page.getNavbar().getCssValue('width')).toEqual('300px');
    browser.actions().click(page.getNavbar().$$('div.module').get(3)).perform();
    browser.driver.sleep(2000);
    expect<any>(page.getNavbar().$$('div.show div').get(2).getAttribute('routerLink')).toEqual('/service-management/ServiceStatistics');
    browser.actions().click(page.getNavbar().$$('div.show div').get(2)).perform();
    browser.waitForAngular();
    expect<any>(browser.getCurrentUrl()).toEqual('http://localhost:49152/service-management/ServiceStatistics');
  });

  it('should have header and title Thống kê yêu cầu dịch vụ', () => {
    expect<any>(serviceStatistics.getHeader().getText()).toEqual('THỐNG KÊ YÊU CẦU DỊCH VỤ');
    expect<any>(browser.getTitle()).toEqual('Thống kê yêu cầu dịch vụ - PMQTVP');
  });

  it('should have fieldset named Thông tin đăng ký', () => {
    expect<any>(serviceStatistics.getFieldSet().getAttribute('legend')).toEqual('Thông tin đăng ký');
  });

  it('should have input label as design', () => {
    expect<any>(serviceStatistics.getInputField().get(0).$('div').getText()).toEqual('Vị trí:');
    expect<any>(serviceStatistics.getInputField().get(1).$('div').getText()).toEqual('Thời gian bắt đầu:');
    expect<any>(serviceStatistics.getInputField().get(2).$('div').getText()).toEqual('Trạng thái:');
    expect<any>(serviceStatistics.getInputField().get(3).$('div').getText()).toEqual('Tên dịch vụ:');
    expect<any>(serviceStatistics.getInputField().get(4).$('div').getText()).toEqual('Người thực hiện:');
  });

  it('should have input label as design', () => {
    // vi tri
    serviceStatistics.getInputField().get(0).$('div input').sendKeys('hoa');
    browser.waitForAngular();
    serviceStatistics.getInputField().get(0).$$('div li').get(0).click();

    // tg

    // serviceStatistics.getInputField().get(1).$$('div input').get(0).sendKeys('');
    // serviceStatistics.getInputField().get(1).$$('div input').get(1).sendKeys('');

    // trang thai
    serviceStatistics.getInputField().get(2).$('div input').click();
    browser.waitForAngular();
    serviceStatistics.getInputField().get(2).$$('div li').get(0).click();
    serviceStatistics.getInputField().get(2).$('div input').click();
    browser.waitForAngular();
    serviceStatistics.getInputField().get(2).$$('div li').get(1).click();
    serviceStatistics.getInputField().get(2).$('div input').click();
    browser.waitForAngular();
    serviceStatistics.getInputField().get(2).$$('div li').get(2).click();
    // ten dich vu
    serviceStatistics.getInputField().get(3).$('div input').sendKeys('Thay');
    browser.waitForAngular();
    serviceStatistics.getInputField().get(3).$$('div li').get(0).click();
    // nguoi thuc hien
    serviceStatistics.getInputField().get(4).$('div input').sendKeys('Hien');
    browser.waitForAngular();
    serviceStatistics.getInputField().get(3).$$('div li').get(0).click();

    browser.pause(10000);

    expect<any>(serviceStatistics.getInputField().get(4).$('div').getText()).toEqual('');
  });
});
