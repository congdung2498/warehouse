import { Component, HostListener, OnInit, ViewChild } from '@angular/core';
import { finalize } from 'rxjs/operators';
import { Constants } from '../../shared/containts';
import { DocumentBoxSearchService } from '../document-box-search.service';
import { MessageService } from 'primeng/api';
import * as cloneDeep from 'lodash/cloneDeep';
import { saveAs } from 'file-saver';
import { Subject } from 'rxjs';
@Component({
    selector: 'app-document-box-search-detail-dialog',
    styleUrls: ['./document-box-search-detail-dialog.component.css'],
    templateUrl: './document-box-search-detail-dialog.component.html',
})
export class DocumentBoxSearchDetailDialogComponent implements OnInit {
    id: number;
    type: any;
    showDialog = false;
    detailList = [];
    detailListBak = [];
    detailTree = [];
    selectedTree: any;
    totalDocumentRecord = 0;
    documentPageNumber = 0;
    pageSize = 10;
    scan = 0;
    notScan = 0;
    loading = false;
    keySearch: string;
    currentRow: any;
    dialogWidth = 1300;
    modelChanged: Subject<string> = new Subject<string>();
    folderId: number;
    @ViewChild('documentTable') documentTable;

    modal_cols = [
        { field: 'path', header: 'Trạng thái scan', width: '200px', class: 'left-align' },
        { field: 'docName', header: 'Tên tài liệu', width: '200px', class: 'left-align' },
        { field: 'constractionName', header: 'Công trình', width: '200px', class: 'left-align' },
        { field: 'contracName', header: 'Hợp đồng', width: '200px', class: 'left-align' },
        { field: 'packageName', header: 'Gói thầu', width: '200px', class: 'left-align' },
        { field: 'projectName', header: 'Dự án', width: '200px', class: 'left-align' },
        { field: 'levelBaoMat', header: 'Mức chia sẻ tài liệu', width: '200px', class: 'left-align' },
    ];

    constructor(private service: DocumentBoxSearchService, private messageService: MessageService
    ) { }

    ngOnInit(): void {
        this.onResize(null);
        this.modelChanged
            .debounceTime(500) // wait 300ms after the last event before emitting last event
            .distinctUntilChanged() // only emit if value is different from previous value
            .subscribe(keyw => this.onSearchDocName(keyw));
    }

    @HostListener('window:resize', ['$event'])
    onResize(event) {
        if (window.innerWidth > 1400) {
            this.dialogWidth = window.innerWidth * 0.8;
        } else {
            this.dialogWidth = window.innerWidth * 0.9;
        }
    }

    onShowDialog(selected) {
        if (!selected || !selected.id || !selected.type || !selected.projectId ) {
            return;
        }
        const body = this.documentTable.containerViewChild.nativeElement.getElementsByClassName('ui-table-scrollable-header-box')[0];
        body.style.marginLeft = '0';
        this.keySearch = '';
        this.id = selected.id;
        this.type = selected.type;
        this.detailList = [];
        this.detailListBak = [];
        this.folderId = selected.folderId;
        this.service
            .getProjectDetail({ projectId: selected.projectId, folderId: selected.folderId })
            .subscribe(resp => {
                if (resp.data && resp.data[0]) {
                    this.buildProjectTree(resp.data[0]);
                    this.getDocumentListInParent(selected.id, selected.type);
                    this.showDialog = true;
                }
            });
    }

    changedKeyword(text: string) {
        this.modelChanged.next(text);
    }

    buildProjectTree(project) {
        const folderNode: any = {
            expandedIcon: 'fa fa-folder-open',
            collapsedIcon: 'fa fa-folder',
            expanded: true,
        };
        const selectedKey = this.type.code + '-' + this.id;

        const projectTree = Object.assign({}, folderNode);
        projectTree.key = Constants.SEARCH_DOC_TYPE.PROJECT.code + '-' + project.projectId;
        projectTree.id = project.projectId;
        projectTree.data = project;
        projectTree.label = project.name;
        projectTree.type = Constants.SEARCH_DOC_TYPE.PROJECT;
        projectTree.children = [];
        const packages = project.packages;
        if (packages && packages.length > 0) {
            packages.forEach(pk => {
                const packageTree = Object.assign({}, folderNode);
                packageTree.key = Constants.SEARCH_DOC_TYPE.PACKAGE.code + '-' + pk.packageId;
                packageTree.id = pk.packageId;
                packageTree.data = pk;
                packageTree.label = pk.name;
                packageTree.type = Constants.SEARCH_DOC_TYPE.PACKAGE;
                packageTree.children = [];
                const contracts = pk.contracts;
                if (contracts && contracts.length > 0) {
                    contracts.forEach(contract => {
                        const contractTree = Object.assign({}, folderNode);
                        contractTree.key = Constants.SEARCH_DOC_TYPE.CONTRACT.code + '-' + contract.contractId;
                        contractTree.id = contract.contractId;
                        contractTree.data = contract;
                        contractTree.label = contract.name;
                        contractTree.type = Constants.SEARCH_DOC_TYPE.CONTRACT;
                        contractTree.children = [];
                        const constructions = contract.constructions;
                        if (constructions && constructions.length > 0) {
                            constructions.forEach(contruction => {
                                const constructionTree = Object.assign({}, folderNode);
                                constructionTree.key = Constants.SEARCH_DOC_TYPE.CONSTRUCTION.code + '-' + contruction.constructionId;
                                constructionTree.id = contruction.constructionId;
                                constructionTree.data = contruction;
                                constructionTree.label = contruction.name;
                                constructionTree.type = Constants.SEARCH_DOC_TYPE.CONSTRUCTION;
                                contractTree.children.push(constructionTree);
                                if (constructionTree.key === selectedKey) {
                                    this.selectedTree = constructionTree;
                                }
                            });
                        }
                        packageTree.children.push(contractTree);
                        if (contractTree.key === selectedKey) {
                            this.selectedTree = contractTree;
                        }
                    });
                }
                projectTree.children.push(packageTree);
                if (packageTree.key === selectedKey) {
                    this.selectedTree = packageTree;
                }
            });
        }
        this.detailTree = [projectTree];
        if (projectTree.key === selectedKey) {
            this.selectedTree = projectTree;
        }
    }

    nodeSelect(parent) {
        this.getDocumentListInParent(parent.node.id, parent.node.type);
    }

    getDocumentListInParent(id, searchType) {
        let treeType;
        switch (searchType.code) {
            case Constants.SEARCH_DOC_TYPE.PROJECT.code:
                treeType = Constants.PROJECT_TREE_NODE_TYPE.PROJECT;
                break;
            case Constants.SEARCH_DOC_TYPE.PACKAGE.code:
                treeType = Constants.PROJECT_TREE_NODE_TYPE.PACKAGE;
                break;
            case Constants.SEARCH_DOC_TYPE.CONTRACT.code:
                treeType = Constants.PROJECT_TREE_NODE_TYPE.CONTRACT;
                break;
            case Constants.SEARCH_DOC_TYPE.CONSTRUCTION.code:
                treeType = Constants.PROJECT_TREE_NODE_TYPE.CONSTRUCTION;
                break;
        }
        this.scan = 0;
        this.notScan = 0;
        this.documentTable.first = 0;
        this.documentPageNumber = 0;
        this.detailList = [];
        this.detailListBak = [];
        this.loading = true;
        this.service.getDocumentListInParent(id, treeType, this.folderId)
            .pipe(
                finalize(() => {
                    this.loading = false;
                })
            )
            .subscribe(resp => {
                if (resp.data) {
                    this.detailList = resp.data;
                    this.detailListBak = resp.data;
                    this.totalDocumentRecord = this.detailList.length;
                    this.detailList.forEach(data => {
                        if (data.path) {
                            this.scan++;
                        } else {
                            this.notScan++;
                        }
                    });
                }
            });
    }

    paginateDocument(event) {
        this.documentPageNumber = event.first;
    }

    onUploadBtn(fileInputField, rowData) {
        fileInputField.value = '';
        fileInputField.click();
        this.currentRow = rowData;
    }

    incomingfile(event) {
        const file = event.target.files[0];
        if (!file || !this.currentRow || !this.currentRow.docId || !this.currentRow.type) {
            return;
        }
        if (!this.validateFile(file)) {
            return;
        }
        const typeUpload = this.getDocumentTypePdf(this.currentRow.type);
        this.service.uploadDocument(file, this.currentRow.docId, typeUpload).subscribe(resp => {
            if (resp.status === Constants.RESPONSE_FILE_TYPE_NOT_MATCH) {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Lỗi',
                    detail: 'Tệp được chọn không đúng định dạng'
                });
            } else if (resp.data) {
                this.messageService.add({
                    severity: 'success',
                    summary: 'Thành công',
                    detail: 'Tải lên thành công'
                });
                this.getDocumentListInParent(this.selectedTree.id, this.selectedTree.type);
            }
        });
    }

    validateFile(file) {
        const accptType = Constants.DOCUMENT_ACCEPT_TYPE;
        let isValidType = true;
        if (file && file.name && file.name.split('.')) {
            const type = file.name.split('.').pop();
            isValidType = (type && accptType.includes(type.toLowerCase()));
        } else {
            isValidType = false;
        }
        if (!isValidType) {
            this.messageService.add({
                severity: 'error',
                summary: 'Lỗi',
                detail: 'Tệp được chọn không đúng định dạng: pdf, docx, doc, svg, xlsx, xls'
            });
            return false;
        }
        return true;
    }

    viewDocument(rowData) {
        if (!rowData || !rowData.docId || !rowData.type) {
            return;
        }
        const typeUpload = this.getDocumentTypePdf(rowData.type);
        this.service.viewDocument({ documentId: rowData.docId, documentType: typeUpload }).subscribe(resp => {
            if (resp.data && resp.data.file && resp.data.fileType) {
                const byteArray = this.getByteArrayFormStr(resp.data.file);
                if (resp.data.fileType === 'pdf') {
                    const blob = new Blob([byteArray], { type: 'application/pdf' });
                    const exportUrl = URL.createObjectURL(blob);
                    window.open(exportUrl);
                } else {
                    const blob = new Blob([byteArray], { type: 'application/' + resp.data.fileType });
                    const data = window.URL.createObjectURL(blob);
                    const link = document.createElement('a');
                    document.body.appendChild(link);
                    link.href = data;
                    link.download = this.getDocNameDownload(rowData.docName, resp.data.fileType);
                    link.click();
                    window.URL.revokeObjectURL(data);
                    link.remove();
                }
            }
        });
    }

    getByteArrayFormStr(str) {
        const byteCharacters = atob(str);
        const byteNumbers = new Array(byteCharacters.length);
        for (let i = 0; i < byteCharacters.length; i++) {
            byteNumbers[i] = byteCharacters.charCodeAt(i);
        }
        const byteArray = new Uint8Array(byteNumbers);
        return byteArray;
    }

    getDocNameDownload(rowDocName, type) {
        let docName = rowDocName ? rowDocName : '';
        if (docName.length > 100) {
            docName = docName.slice(0, 100);
        }
        docName += ('0' + new Date().getDate()).slice(-2) + '_'
            + ('0' + (new Date().getMonth() + 1)).slice(-2) + '_' + new Date().getFullYear() + '_' + new Date().getHours()
            + 'h' + new Date().getMinutes() + 'm' + new Date().getSeconds() + 's';
        docName = docName.trim() + '.' + type;
        return docName;
    }

    getDocumentTypePdf(type: string) {
        switch (type) {
            case 'duan':
                return 3;
            case 'goithau':
                return 4;
            case 'hopdong':
                return 5;
            case 'congtrinh':
                return 6;
        }
    }

    onSearchDocName(docName) {
        this.scan = 0;
        this.notScan = 0;
        this.documentTable.first = 0;
        this.documentPageNumber = 0;
        this.detailList = [];
        if (docName && docName.trim()) {
            docName = this.compoundUnicode(docName);
            this.detailList = this.detailListBak.filter(detail => detail.docName.toLowerCase().includes(docName.trim().toLowerCase()));
        } else {
            this.detailList = this.detailListBak;
        }
        this.detailList.forEach(data => {
            if (data.path) {
                this.scan++;
            } else {
                this.notScan++;
            }
        });
        this.totalDocumentRecord = this.detailList.length;
    }

    onExport() {
        if (!this.detailList || this.detailList.length === 0) {
            this.messageService.add({
                severity: 'warn',
                summary: 'Thông báo',
                detail: 'Không tồn tại dữ liệu'
            });
            return;
        }
        const exportData = cloneDeep(this.detailList);
        exportData.map(data => {
            data.path = data.path ? 'Đã scan' : 'Chưa scan';
        });
        const folderName = this.getCurrentName();
        // push to api
        this.service.exportSearchResult(exportData).subscribe(res => {
            const fileName = 'Danh sách tài liệu ' + folderName + '.xlsx';
            const file: Blob = new Blob([res.body], { type: 'application/xlsx' });
            saveAs(file, fileName);
        });
    }

    getCurrentName(): string {
        let name = '';
        switch (this.selectedTree.type.code) {
            case Constants.SEARCH_DOC_TYPE.PROJECT.code:
                name = 'dự án ' + this.selectedTree.label;
                break;
            case Constants.SEARCH_DOC_TYPE.PACKAGE.code:
                name = 'gói thầu ' + this.selectedTree.label;
                break;
            case Constants.SEARCH_DOC_TYPE.CONTRACT.code:
                name = 'hợp đồng ' + this.selectedTree.label;
                break;
            case Constants.SEARCH_DOC_TYPE.CONSTRUCTION.code:
                name = 'công trình ' + this.selectedTree.label;
                break;
        }
        if (name.length > 100) {
            name = name.slice(0, 100);
        }
        // name = name.replace(/[\W_]/g, '_');
        return name.trim();
    }

    compoundUnicode(str) {
        return `${str}`
            .replace(/\u0065\u0309/g, '\u1EBB') //  ẻ
            .replace(/\u0065\u0301/g, '\u00E9') //  é
            .replace(/\u0065\u0300/g, '\u00E8') //  è
            .replace(/\u0065\u0323/g, '\u1EB9') //  ẹ
            .replace(/\u0065\u0303/g, '\u1EBD') //  ẽ
            .replace(/\u00EA\u0309/g, '\u1EC3') //  ể
            .replace(/\u00EA\u0301/g, '\u1EBF') //  ế
            .replace(/\u00EA\u0300/g, '\u1EC1') //  ề
            .replace(/\u00EA\u0323/g, '\u1EC7') //  ệ
            .replace(/\u00EA\u0303/g, '\u1EC5') //  ễ
            .replace(/\u0079\u0309/g, '\u1EF7') //  ỷ
            .replace(/\u0079\u0301/g, '\u00FD') //  ý
            .replace(/\u0079\u0300/g, '\u1EF3') //  ỳ
            .replace(/\u0079\u0323/g, '\u1EF5') //  ỵ
            .replace(/\u0079\u0303/g, '\u1EF9') //  ỹ
            .replace(/\u0075\u0309/g, '\u1EE7') //  ủ
            .replace(/\u0075\u0301/g, '\u00FA') //  ú
            .replace(/\u0075\u0300/g, '\u00F9') //  ù
            .replace(/\u0075\u0323/g, '\u1EE5') //  ụ
            .replace(/\u0075\u0303/g, '\u0169') //  ũ
            .replace(/\u01B0\u0309/g, '\u1EED') //  ử
            .replace(/\u01B0\u0301/g, '\u1EE9') //  ứ
            .replace(/\u01B0\u0300/g, '\u1EEB') //  ừ
            .replace(/\u01B0\u0323/g, '\u1EF1') //  ự
            .replace(/\u01B0\u0303/g, '\u1EEF') //  ữ
            .replace(/\u0069\u0309/g, '\u1EC9') //  ỉ
            .replace(/\u0069\u0301/g, '\u00ED') //  í
            .replace(/\u0069\u0300/g, '\u00EC') //  ì
            .replace(/\u0069\u0323/g, '\u1ECB') //  ị
            .replace(/\u0069\u0303/g, '\u0129') //  ĩ
            .replace(/\u006F\u0309/g, '\u1ECF') //  ỏ
            .replace(/\u006F\u0301/g, '\u00F3') //  ó
            .replace(/\u006F\u0300/g, '\u00F2') //  ò
            .replace(/\u006F\u0323/g, '\u1ECD') //  ọ
            .replace(/\u006F\u0303/g, '\u00F5') //  õ
            .replace(/\u01A1\u0309/g, '\u1EDF') //  ở
            .replace(/\u01A1\u0301/g, '\u1EDB') //  ớ
            .replace(/\u01A1\u0300/g, '\u1EDD') //  ờ
            .replace(/\u01A1\u0323/g, '\u1EE3') //  ợ
            .replace(/\u01A1\u0303/g, '\u1EE1') //  ỡ
            .replace(/\u00F4\u0309/g, '\u1ED5') //  ổ
            .replace(/\u00F4\u0301/g, '\u1ED1') //  ố
            .replace(/\u00F4\u0300/g, '\u1ED3') //  ồ
            .replace(/\u00F4\u0323/g, '\u1ED9') //  ộ
            .replace(/\u00F4\u0303/g, '\u1ED7') //  ỗ
            .replace(/\u0061\u0309/g, '\u1EA3') //  ả
            .replace(/\u0061\u0301/g, '\u00E1') //  á
            .replace(/\u0061\u0300/g, '\u00E0') //  à
            .replace(/\u0061\u0323/g, '\u1EA1') //  ạ
            .replace(/\u0061\u0303/g, '\u00E3') //  ã
            .replace(/\u0103\u0309/g, '\u1EB3') //  ẳ
            .replace(/\u0103\u0301/g, '\u1EAF') //  ắ
            .replace(/\u0103\u0300/g, '\u1EB1') //  ằ
            .replace(/\u0103\u0323/g, '\u1EB7') //  ặ
            .replace(/\u0103\u0303/g, '\u1EB5') //  ẵ
            .replace(/\u00E2\u0309/g, '\u1EA9') //  ẩ
            .replace(/\u00E2\u0301/g, '\u1EA5') //  ấ
            .replace(/\u00E2\u0300/g, '\u1EA7') //  ầ
            .replace(/\u00E2\u0323/g, '\u1EAD') //  ậ
            .replace(/\u00E2\u0303/g, '\u1EAB') //  ẫ
            .replace(/\u0045\u0309/g, '\u1EBA') //  Ẻ
            .replace(/\u0045\u0301/g, '\u00C9') //  É
            .replace(/\u0045\u0300/g, '\u00C8') //  È
            .replace(/\u0045\u0323/g, '\u1EB8') //  Ẹ
            .replace(/\u0045\u0303/g, '\u1EBC') //  Ẽ
            .replace(/\u00CA\u0309/g, '\u1EC2') //  Ể
            .replace(/\u00CA\u0301/g, '\u1EBE') //  Ế
            .replace(/\u00CA\u0300/g, '\u1EC0') //  Ề
            .replace(/\u00CA\u0323/g, '\u1EC6') //  Ệ
            .replace(/\u00CA\u0303/g, '\u1EC4') //  Ễ
            .replace(/\u0059\u0309/g, '\u1EF6') //  Ỷ
            .replace(/\u0059\u0301/g, '\u00DD') //  Ý
            .replace(/\u0059\u0300/g, '\u1EF2') //  Ỳ
            .replace(/\u0059\u0323/g, '\u1EF4') //  Ỵ
            .replace(/\u0059\u0303/g, '\u1EF8') //  Ỹ
            .replace(/\u0055\u0309/g, '\u1EE6') //  Ủ
            .replace(/\u0055\u0301/g, '\u00DA') //  Ú
            .replace(/\u0055\u0300/g, '\u00D9') //  Ù
            .replace(/\u0055\u0323/g, '\u1EE4') //  Ụ
            .replace(/\u0055\u0303/g, '\u0168') //  Ũ
            .replace(/\u01AF\u0309/g, '\u1EEC') //  Ử
            .replace(/\u01AF\u0301/g, '\u1EE8') //  Ứ
            .replace(/\u01AF\u0300/g, '\u1EEA') //  Ừ
            .replace(/\u01AF\u0323/g, '\u1EF0') //  Ự
            .replace(/\u01AF\u0303/g, '\u1EEE') //  Ữ
            .replace(/\u0049\u0309/g, '\u1EC8') //  Ỉ
            .replace(/\u0049\u0301/g, '\u00CD') //  Í
            .replace(/\u0049\u0300/g, '\u00CC') //  Ì
            .replace(/\u0049\u0323/g, '\u1ECA') //  Ị
            .replace(/\u0049\u0303/g, '\u0128') //  Ĩ
            .replace(/\u004F\u0309/g, '\u1ECE') //  Ỏ
            .replace(/\u004F\u0301/g, '\u00D3') //  Ó
            .replace(/\u004F\u0300/g, '\u00D2') //  Ò
            .replace(/\u004F\u0323/g, '\u1ECC') //  Ọ
            .replace(/\u004F\u0303/g, '\u00D5') //  Õ
            .replace(/\u01A0\u0309/g, '\u1EDE') //  Ở
            .replace(/\u01A0\u0301/g, '\u1EDA') //  Ớ
            .replace(/\u01A0\u0300/g, '\u1EDC') //  Ờ
            .replace(/\u01A0\u0323/g, '\u1EE2') //  Ợ
            .replace(/\u01A0\u0303/g, '\u1EE0') //  Ỡ
            .replace(/\u00D4\u0309/g, '\u1ED4') //  Ổ
            .replace(/\u00D4\u0301/g, '\u1ED0') //  Ố
            .replace(/\u00D4\u0300/g, '\u1ED2') //  Ồ
            .replace(/\u00D4\u0323/g, '\u1ED8') //  Ộ
            .replace(/\u00D4\u0303/g, '\u1ED6') //  Ỗ
            .replace(/\u0041\u0309/g, '\u1EA2') //  Ả
            .replace(/\u0041\u0301/g, '\u00C1') //  Á
            .replace(/\u0041\u0300/g, '\u00C0') //  À
            .replace(/\u0041\u0323/g, '\u1EA0') //  Ạ
            .replace(/\u0041\u0303/g, '\u00C3') //  Ã
            .replace(/\u0102\u0309/g, '\u1EB2') //  Ẳ
            .replace(/\u0102\u0301/g, '\u1EAE') //  Ắ
            .replace(/\u0102\u0300/g, '\u1EB0') //  Ằ
            .replace(/\u0102\u0323/g, '\u1EB6') //  Ặ
            .replace(/\u0102\u0303/g, '\u1EB4') //  Ẵ
            .replace(/\u00C2\u0309/g, '\u1EA8') //  Ẩ
            .replace(/\u00C2\u0301/g, '\u1EA4') //  Ấ
            .replace(/\u00C2\u0300/g, '\u1EA6') //  Ầ
            .replace(/\u00C2\u0323/g, '\u1EAC') //  Ậ
            .replace(/\u00C2\u0303/g, '\u1EAA') //  Ẫ
    }
}
