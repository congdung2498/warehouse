import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/components/common/api';
import { Router } from '@angular/router';
import { Constants } from '../../shared/containts';
import { UserInfo } from '../../shared/userInfo';
import { TokenStorage } from '../../shared/token.storage';
@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
    menu: boolean[] = [false, false, false, false, false, false, false, false, false];
    hover: boolean;
    userInfor: UserInfo = null;
    constructor(private router: Router, private tokenStorage: TokenStorage) { }

    ngOnInit() {

    }
    moduleClick(module) {
        for (let index = 1; index < 9; index++) {
            JSON.stringify(module) === JSON.stringify('module' + index) ? this.menu[index] = !this.menu[index] : this.menu[index] = false;
        }
    }
    hoverLeft(event) {
        if (event === true) {
            this.hover = true;
        } else {
            this.hover = false;
            for (let index = 1; index < 8; index++) {
                this.menu[index] = false;
            }
        }
    }

    logOut() {
        if (this.tokenStorage.getUserInfo().role.includes('PMQT_Canhve')) {
            this.tokenStorage.clearStorage();
            this.router.navigateByUrl('/login/vebinh');
        } else {
            this.tokenStorage.signOut();
        }
    }

    isLogged() {
        if (this.tokenStorage.isLogged()) {
            this.userInfor = this.tokenStorage.getUserInfo();
            return true;
        } else {
            this.userInfor = null;
            return false;
        }
    }

    checkPermission(url: string) {
        return this.tokenStorage.checkPermission(url);
    }

    isPermission(url: string): boolean {
        return this.tokenStorage.isPermission(url);
    }
}
