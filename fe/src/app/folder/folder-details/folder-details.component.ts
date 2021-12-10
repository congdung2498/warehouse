import { Component, OnInit, OnDestroy } from '@angular/core';
import { FolderService } from '../folder.service';
import { Folder } from '../Entity/Folder';
import { MessageService } from 'primeng/api';
import { ActivatedRoute, Router } from '@angular/router';
import { Project } from '../Entity/Project';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'folder-details',
  templateUrl: './folder-details.component.html',
  styleUrls: ['./folder-details.component.css']
})
export class FolderDetailsComponent implements OnInit, OnDestroy {

  EDITING = "EDITING";
  ADDING = "ADDING";

  isLoading:boolean = false;
  addingOrEditing: string = "";
  selectedFolder: Folder = new Folder();
  projectsInFolder: Project[] = [];  
  pagingState: string;
  totalRecord: number = 0;
  pageSize: number = 10;
  pageNumber: number = 1;

  keyword: string = "";
  unhandleProjects: Project[] = [];
  unhandleProjectsInit: Project[] = [];
  totalUnhandleProjects: number = 0;
  unhandleProjectsPageNumber: number = 1;
  isShowModal: boolean = false;

  selectedProject: Project = new Project();
  dataB4Work:any;
  isShowFolderDetailsModal: boolean = false;

  selectedPackageName: string = "";
  selectedContractName: string = "";
  selectedConstructionName: string = "";

  isSelectAll: boolean = false;

  isShowingDoc = {project: true,
                  package: true,
                  contract: true}

  cols = [
    { field: "", header: "STT", width: "5%" },    
    { field: "", header: "Dự án", width: "80%" },
    // { field: "", header: "Mã dự án", width: "35%" },
    { field: "", header: "Thao Tác", width: "15%" },
  ];

  unhandleProjectDocCols = [
    { field: "", header: "STT", width: "10%" },    
    { field: "", header: "Tài liệu dự án", width: "70%" },
    { field: "", header: "Thao Tác", width: "20%" },
  ];

  notChar: RegExp = /[%&_*\\'<>]/g;
  isBindingProjectToFolder: boolean = false;

  constructor(
    private folderService: FolderService, 
    private messageService: MessageService, 
    private route: ActivatedRoute,
    private router: Router, 
    private title: Title,) {
      this.title.setTitle("Quản lý hồ sơ");
    }

  ngOnInit() {
    let storedFolder = JSON.parse(localStorage.getItem("selectedFolder"));
    if(storedFolder == null){
      localStorage.clear();
      this.backToSearch();
    }
    else{
      localStorage.setItem("lastScreen", "FOLDER_DETAILS_SCREEN");
      this.selectedFolder = storedFolder;
      this.getProjectsInFolder();
    } 
  }

  getProjectsInFolder(){
    let selectedFolderId = parseInt(this.selectedFolder.folderId);
    this.folderService.getProjectsInFolder(selectedFolderId)
    .subscribe(resp =>{
      if(resp.status && resp.status == 1) this.bindData(resp);
      else this.showErrorMsg();
    })
  }

  bindData(resp: any){
    if(resp.data){
      this.projectsInFolder = resp.data;
      this.totalRecord = resp.data.length;      
    }
    else{
      this.projectsInFolder = [];
    }
  }

  addMorePrjClick(){
    let folderId = parseInt(this.selectedFolder.folderId);
    this.folderService.getProjectsNotInFolder(folderId)
    .subscribe(resp=>{
      if(resp.status && resp.status == 1) this.showAddProjectsModal(resp);
      else this.showErrorMsg();
    })    
  }

  showAddProjectsModal(resp: any){
    if(resp.data){
      this.unhandleProjectsInit = resp.data;
      this.totalUnhandleProjects = resp.data.length;      
    }
    else{
      this.unhandleProjectsInit = [];
    }
    this.unhandleProjects = this.unhandleProjectsInit;
    this.isShowModal = true;
  }

  detectBackSpace(event:any){
    console.log(event)
  }

  // get project tree 
  // isInFolder = true: with documents already inside current selected folder and documents not in any folder
  // isInFolder = false: documents of project not in any folder only
  showProjectDetails(project: Project, isInFolder: boolean){
    this.isLoading = true;    
    let folderId: number;

    if(isInFolder){
      folderId = parseInt(this.selectedFolder.folderId);
      this.addingOrEditing = this.EDITING;
    } 
    else{
      folderId = 0;
      this.addingOrEditing = this.ADDING;
    }

    let projectId = parseInt(project.projectId);
    this.folderService.getProjectDetails(folderId, projectId)
    .subscribe(resp=>{
      console.log(resp);
      if(resp.status && resp.status == 1) this.showProjectDetailsModal(resp);
      else this.showErrorMsg();
      this.isLoading = false;
    })
  }

  showProjectDetailsModal(resp: any){
    if(resp.data){      
      //this.selectedProject = resp.data[0];
      this.selectedProject = this.filterOutObjectHasNoDoc(resp.data[0]);
      this.assignIsInFolderForDocs();
      this.checkForTickAll();
      this.dataB4Work = JSON.stringify(this.selectedProject);
      this.isShowFolderDetailsModal = true;
    }
  }

  filterOutObjectHasNoDoc(project: Project){
    if(project.packages){
      project.packages = project.packages.filter(pkg=>{
        if (pkg.docs && pkg.docs.length > 0) return true;
        else return false;
      })
    }

    if(project.contracts){
      project.contracts = project.contracts.filter(contract=>{
        let contractHasDoc = false;

        if(contract.docs && contract.docs.length > 0) contractHasDoc = true;

        if(contract.constructions){
          contract.constructions = contract.constructions.filter(construction=>{
            let consHasDoc = false;
            if(construction.docs && construction.docs.length > 0){
              contractHasDoc = true;
              consHasDoc = true;
            }
            return consHasDoc;
          })
        }
        
        return contractHasDoc;
      })
    }

    return project;
  }

  assignIsInFolderForDocs(){

    if(this.selectedProject.docs && this.selectedProject.docs.length>0){
      this.selectedProject.docs.forEach(doc=>{
        if(doc.folderId != 0) doc.isInFolder = true;
        else doc.isInFolder= false;
      })
    }
    
    if(this.selectedProject.packages){
      this.selectedProject.packages.forEach(pkg=>{
        if (pkg.docs && pkg.docs.length > 0){
          pkg.docs.forEach(pkgDoc=>{
            if(pkgDoc.folderId != 0) pkgDoc.isInFolder = true;
            else pkgDoc.isInFolder= false;
          });
        }
      });
    }

    if(this.selectedProject.contracts){
      this.selectedProject.contracts.forEach(contract=>{

        if(contract.docs && contract.docs.length > 0){
          contract.docs.forEach(ctDoc=>{
            if(ctDoc.folderId != 0) ctDoc.isInFolder = true;
            else ctDoc.isInFolder= false;
          })
        }

        if(contract.constructions){
          contract.constructions.forEach(construction=>{
            if(construction.docs && construction.docs.length > 0){
              construction.docs.forEach(consDoc=>{
                if(consDoc.folderId != 0) consDoc.isInFolder = true;
                else consDoc.isInFolder= false;
              })
            }
          })
        }
      })
    }
  }

  updateBindProjectToFolder(){
    
    this.isBindingProjectToFolder = true;
    //check if any checbox checked or unchecked
    let checkChanges = (JSON.stringify(this.selectedProject) !== this.dataB4Work);    
    
    if(checkChanges){
      let postData = this.prepareData();
      
      this.folderService.addDocsInProjectToFolder(postData)
      .subscribe(resp=>{
        if(resp.status && resp.status == 1){
          this.showSuccessMsg();
          this.getProjectsInFolder();            
          this.isShowModal = false;
          this.isShowFolderDetailsModal = false;
          this.isBindingProjectToFolder = false;          
        } 
        else{
          this.showErrorMsg();
          this.isBindingProjectToFolder = false;
        }
      });
      this.addingOrEditing = "";      
    }
    else{
      this.messageService.add({
        severity: "info",
        summary: "Thông báo:",
        detail: "Không có sự thay đổi, vui lòng ấn hủy để đóng lại."
      });
      this.isBindingProjectToFolder = false;
    }
  }

  giveCorrectAction(doc: any){
    let DO_NOTHING = 0;
    let ADD_ACTION = 4;
    let REMOVE_ACTION = 5;
    let actionNumber = DO_NOTHING;

    if(this.addingOrEditing == this.ADDING){
      if(doc.isInFolder === true) actionNumber = ADD_ACTION;
    }
    else if(this.addingOrEditing = this.EDITING){
      if(doc.isInFolder === true){
        if(doc.folderId == 0) actionNumber = ADD_ACTION;
      }
      else{
        if(doc.folderId != 0) actionNumber = REMOVE_ACTION;
      }
    }

    return actionNumber;
  }

  prepareData(){
    let tinBox:any = new Object();
    tinBox.tinBoxId = this.selectedFolder.tinBoxId;
    tinBox.name = this.selectedFolder.tinBoxName;
    tinBox.qrCode = this.selectedFolder.tinBoxQrCode;

    let folder:any = new Object();
    folder.folderId = this.selectedFolder.folderId;
    folder.name = this.selectedFolder.folderName;
    folder.qrCode = this.selectedFolder.folderQrCode;

    tinBox.folders = [folder]

    let project = this.selectedProject;

    if(project.docs && project.docs.length>0){
      project.docs.forEach(doc=>{
        doc.action = this.giveCorrectAction(doc);        
      });
    }
    
    if(project.packages){
      project.packages.forEach(pkg=>{
        if (pkg.docs && pkg.docs.length > 0){
          pkg.docs.forEach(doc=>{
            doc.action = this.giveCorrectAction(doc);
          });
        }
      });
    }

    if(project.contracts){
      project.contracts.forEach(contract=>{

        if(contract.docs && contract.docs.length > 0){
          contract.docs.forEach(doc=>{
            doc.action = this.giveCorrectAction(doc);
          })
        }

        if(contract.constructions){
          contract.constructions.forEach(construction=>{
            if(construction.docs && construction.docs.length > 0){
              construction.docs.forEach(doc=>{
                doc.action = this.giveCorrectAction(doc);
              })
            }
          })
        }
      })
    }
    
    folder.projects = [project];

    console.log(tinBox)
    return tinBox;
  }

  showSuccessMsg(){
    this.messageService.add({
      severity: "info",
      summary: "Thành công:",
      detail: "Cập nhật hồ sơ thành công!"
    });
  }

  showErrorMsg(){
    this.messageService.add({
      severity: "error",
      summary: "Cảnh báo lỗi:",
      detail: "Có lỗi không xác định. Vui lòng thử lại!"
    });
  }

  toggleSelectAll(){

    if(this.selectedProject.docs && this.selectedProject.docs.length>0){
      this.selectedProject.docs.forEach(doc=>{
        doc.isInFolder = this.isSelectAll;
      });
    }
    
    if(this.selectedProject.packages){
      this.selectedProject.packages.forEach(pkg=>{
        if (pkg.docs && pkg.docs.length > 0){
          pkg.docs.forEach(pkgDoc=>{
            pkgDoc.isInFolder = this.isSelectAll;;
          });
        }
      });
    }

    if(this.selectedProject.contracts){
      this.selectedProject.contracts.forEach(contract=>{

        if(contract.docs && contract.docs.length > 0){
          contract.docs.forEach(ctDoc=>{
            ctDoc.isInFolder = this.isSelectAll;
          })
        }

        if(contract.constructions){
          contract.constructions.forEach(construction=>{
            if(construction.docs && construction.docs.length > 0){
              construction.docs.forEach(consDoc=>{
                consDoc.isInFolder = this.isSelectAll;
              })
            }
          })
        }
      })
    }
  }

  checkForTickAll(){

    let cbxStates: boolean[] = [];
    let willCheckAll: boolean = true;

    if(this.selectedProject.docs && this.selectedProject.docs.length>0){
      this.selectedProject.docs.forEach(doc=>{
        cbxStates.push(doc.isInFolder);
      });
    }
    
    if(this.selectedProject.packages){
      this.selectedProject.packages.forEach(pkg=>{
        if (pkg.docs && pkg.docs.length > 0){
          pkg.docs.forEach(doc=>{
            cbxStates.push(doc.isInFolder);
          });
        }
      });
    }

    if(this.selectedProject.contracts){
      this.selectedProject.contracts.forEach(contract=>{

        if(contract.docs && contract.docs.length > 0){
          contract.docs.forEach(doc=>{
            cbxStates.push(doc.isInFolder);
          })
        }

        if(contract.constructions){
          contract.constructions.forEach(construction=>{
            if(construction.docs && construction.docs.length > 0){
              construction.docs.forEach(doc=>{
                cbxStates.push(doc.isInFolder);
              })
            }
          })
        }
      })
    }

    if(cbxStates.length>0){
      if(cbxStates.length == 1){
        this.isSelectAll = cbxStates[0];
      }
      else{
        for(let i = 0; i<cbxStates.length - 1; i++){
          if(!cbxStates[i] || cbxStates[i] != cbxStates[i+1]){
            willCheckAll = false;
          }
        }

        this.isSelectAll = willCheckAll;
      }      
    }
  }

  handleSearch(table) {
    table.first = 0;

    if(this.keyword.trim() == "") this.unhandleProjects = this.unhandleProjectsInit;

    if (this.keyword.trim() != "" && this.unhandleProjectsInit.length > 0) {
      this.unhandleProjects = this.unhandleProjectsInit.filter(row => {
        let result = false;
        let phrase = this.keyword.toLowerCase().trim();
        let prjName = row.name.toLowerCase();
        let prjCode = row.code.toLowerCase();
        if (prjName.indexOf(phrase) != -1 || prjCode.indexOf(phrase) != -1) {
          result = true;
        }
        return result;
      });
    }
  }

  closeFolderDetailsModal(){
    this.isShowFolderDetailsModal = false;
  }

  closeAddMorePrjModal(projectNotInAnyFolderTable:any){
    this.keyword='';
    projectNotInAnyFolderTable.first = 0;
  }
  

  paginate(event: any){
    this.pageNumber = event.first/this.pageSize + 1;
  }

  paginateUnhandleProject(event:any){
    this.unhandleProjectsPageNumber = event.first/this.pageSize + 1
  }

  toggleShowingDoc(type: string){
    if(this.isShowingDoc[type]) this.isShowingDoc[type] = false;
    else this.isShowingDoc[type] = true;
  }

  toggleShow(uniqueId: string){
    let node = document.getElementById(uniqueId);
    if(node.hidden) node.hidden = false;
    else node.hidden = true;
  }

  backToSearch(){
    this.router.navigate(['folder/folder-search']);
  }

  preventSpecialChar(input) {
    if (this.notChar.test(input.value)) {
      input.value = input.value.replace(this.notChar, '');
    }
  }

  ngOnDestroy(){
    if (this.router.routerState.snapshot.url.indexOf('folder-search') == -1) {
      localStorage.clear();
    }
  }
   
}

