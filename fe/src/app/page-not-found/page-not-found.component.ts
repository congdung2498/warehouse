import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/components/common/api';
import { Router } from '@angular/router';

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.css']
})
export class PageNotFoundComponent implements OnInit {

  constructor(private message: MessageService, private router: Router) { }

  ngOnInit() {
    this.message.add({
      severity: 'error',
      summary: 'Lỗi:',
      detail: 'Địa chỉ Đồng chí vừa truy cập không tồn tại!'
    });
    this.router.navigate(['/'], {});
  }

}
