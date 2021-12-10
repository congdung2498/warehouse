import { Component } from '@angular/core';
import { HTTPStatus } from './shared/oauth-http-interceptor';
import {ConfirmationService, MessageService} from "primeng/api";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [MessageService]
})
export class AppComponent {
  title = 'VTNet by RIKKEI';
  HTTPActivity: boolean;
  constructor(private httpStatus: HTTPStatus,
              private messageService: MessageService ,
              private confirmationService: ConfirmationService
  ) {
    this.httpStatus.getHttpStatus().subscribe(
      (status: boolean) => {
        this.HTTPActivity = status;
      });
  }


  showSuccess(detail: string) {
    this.messageService.add({key : 't2',severity:'success', summary: 'Thành công', detail: detail});
  }
  showInfo(detail: string) {
    this.messageService.add({key : 't2',severity:'info', summary: 'Chi tiết', detail: detail});
  }
  showWarn(detail: string) {
    this.messageService.add({key : 't2',severity:'warn', summary: 'Lỗi', detail: detail});
  }
  errorValidateDate(detail: string ) {
    this.messageService.add({key : 't2',severity:'error', summary: 'Lỗi', detail: detail});
  }
  errorValidateDateNow( detail: string) {

  this.messageService.add({key : 't2',severity:'error', summary: 'Lỗi', detail: detail});
  }

  /**
   * confirmMessage
   */
  confirmMessage(messageCode: string, accept: Function, reject: Function) {
    const message = 'Đ/c có muốn thực hiện thao tác';
    return this.confirmationService.confirm({
      message: message,
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: accept,
      reject: reject
    });
  }

  // @HostListener('window:onbeforeunload', ['$event'])
  // clearLocalStorage(event) {
  //     localStorage.clear();
  // }
  // @HostListener('window:', ['$event'])
  // AddLocalStorage(event) {
  //     localStorage.clear();
  // }
}
