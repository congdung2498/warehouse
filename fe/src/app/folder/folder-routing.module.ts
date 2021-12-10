import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FolderSearchComponent } from './folder-search-by-qr-code/folder-search.component';
import { FolderDetailsComponent } from './folder-details/folder-details.component';

const routes: Routes = [
  {
    path: 'folder-search',
    component: FolderSearchComponent
  },
  {
    path: 'folder-details',
    component: FolderDetailsComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FolderRoutingModule { }
